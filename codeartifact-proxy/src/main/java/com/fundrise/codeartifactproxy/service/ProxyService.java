package com.fundrise.codeartifactproxy.service;

import com.amazonaws.services.codeartifact.AWSCodeArtifact;
import com.amazonaws.services.codeartifact.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.protocol.HttpContext;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import reactor.core.publisher.Flux;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
@AllArgsConstructor
@Slf4j
public class ProxyService {

    private AWSCodeArtifact awsCodeArtifact;

    private static final String FCP_CONSTRUCT_LIB_ENDPOINT = "https://fundrise-631856996037.d.codeartifact.us-east-1.amazonaws.com/npm/fcp-construct-lib/";

    public ResponseEntity<Object> getPackageMetadata(String packageName) throws IOException, InterruptedException {
        var authorizationTokenRequest = new GetAuthorizationTokenRequest();
        authorizationTokenRequest.withDomain("fundrise");
        var authorizationTokenResult = awsCodeArtifact.getAuthorizationToken(authorizationTokenRequest);
        log.info("Authorization token {}", authorizationTokenResult.getAuthorizationToken());
        var client = HttpClient.newHttpClient();
        var request = java.net.http.HttpRequest.newBuilder(
                        URI.create(FCP_CONSTRUCT_LIB_ENDPOINT+packageName))
                .header("accept", "application/json")
                .header("Authorization", "Bearer "+ authorizationTokenResult.getAuthorizationToken())
                .build();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        var responseBody = new ObjectMapper().readValue(response.body(), Object.class);
        HttpHeaders responseHeaders = new HttpHeaders();
        var responseEntity = ResponseEntity.ok().headers(responseHeaders).body(responseBody);
        log.info("Headers {}", response.headers());
        return responseEntity;
    }

    public ResponseEntity getPackageContent(String packageZipName, HttpServletResponse httpServletResponse) throws IOException, InterruptedException {
        var authorizationTokenRequest = new GetAuthorizationTokenRequest();
        authorizationTokenRequest.withDomain("fundrise");
        log.info("URL:::" + URI.create(FCP_CONSTRUCT_LIB_ENDPOINT + packageZipName));
        var authorizationTokenResult = awsCodeArtifact.getAuthorizationToken(authorizationTokenRequest);
        log.info("Authorization token {}", authorizationTokenResult.getAuthorizationToken());

        var client = HttpClient.newHttpClient();
        var request = java.net.http.HttpRequest.newBuilder(
                        URI.create(FCP_CONSTRUCT_LIB_ENDPOINT + packageZipName))

                .header("Accept", MediaType.APPLICATION_OCTET_STREAM_VALUE)
                .header("Accept-Encoding", "gzip, deflate, br")
                .header("Authorization", "Bearer "+ authorizationTokenResult.getAuthorizationToken())
                .build();

        var response = client.send(request, java.net.http.HttpResponse.BodyHandlers.ofByteArray());
        HttpHeaders responseHeaders = new HttpHeaders();
        var responseEntity = ResponseEntity.ok().headers(responseHeaders).body(response.body());
        log.info("Headers {}", response.headers());
        log.info("Response Code {}", response.statusCode());
        if(response.statusCode() == HttpStatus.FOUND.value()){
            var location = response.headers().firstValue(HttpHeaders.LOCATION);
            if(location.isPresent()) {
                log.info("Location :: {}", response.headers().firstValue(HttpHeaders.LOCATION));
                log.info("Location Encoded:: {}", location.get());
                log.info("Location Decoded:: {}",java.net.URLDecoder.decode(location.get(), StandardCharsets.UTF_8.name()));
                DefaultUriBuilderFactory defaultUriBuilderFactory = new DefaultUriBuilderFactory();
                defaultUriBuilderFactory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);

                RequestEntity<Void> redirectRequest = RequestEntity.get(location.get())
                        .accept(MediaType.APPLICATION_OCTET_STREAM).header("Accept-Encoding", "gzip, deflate, br").build();

                final RestTemplate restTemplate = new RestTemplate();
                restTemplate.setUriTemplateHandler(defaultUriBuilderFactory);
                restTemplate.setMessageConverters(Arrays.asList(jacksonSupportsMoreTypes(), new FormHttpMessageConverter()));
                HttpHeaders headers = new HttpHeaders();
                headers.add("Accept", MediaType.APPLICATION_OCTET_STREAM_VALUE);


                restTemplate.execute(
                        location.get(),
                        HttpMethod.GET,
                        null,
                        responseExtractor -> {
                            IOUtils.copy(responseExtractor.getBody(), httpServletResponse.getOutputStream());
                            return null;
                        });

                /*

                WebClient webClient = WebClient.builder().build();
                var dataBufferFlux = webClient.get()
                        .uri(URI.create( location.get()))
                        .accept(MediaType.APPLICATION_OCTET_STREAM)
                        .header("Accept-Encoding", "gzip, deflate, br")
                        .retrieve()
                        .bodyToFlux(DataBuffer.class);
//                DataBufferUtils.write(dataBufferFlux, Paths.get("package.zip"), StandardOpenOption.CREATE).block(); //Creates new file or overwrites exisiting file

                 */


                return ResponseEntity.ok().build();
            }
        }


//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("Authorization", "Bearer "+authorizationTokenResult.getAuthorizationToken());


        /*
        final RestTemplate restTemplate = new RestTemplate();
        final HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        final CloseableHttpClient httpClient = HttpClientBuilder.create()
                .build();
        factory.setHttpClient(httpClient);
        restTemplate.setRequestFactory(factory);
        restTemplate.getMessageConverters().add(jacksonSupportsMoreTypes());
        RequestEntity<Void> request = RequestEntity.get(FCP_CONSTRUCT_LIB_ENDPOINT + packageZipName)
                .accept(MediaType.ALL).header("Authorization", "Bearer "+authorizationTokenResult.getAuthorizationToken()).build();
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new RedirectInterceptor());
        restTemplate.setInterceptors(interceptors);
//        HttpEntity<String> entity = new HttpEntity<String>(headers);
        var zipResponse = restTemplate.exchange(request, InputStream.class);



        CloseableHttpClient httpClient = HttpClientBuilder
                .create()
                .setRedirectStrategy( new DefaultRedirectStrategy() {

                    @Override
                    public boolean isRedirected(HttpRequest request, HttpResponse response,
                                                HttpContext context) throws org.apache.http.ProtocolException {

                        System.out.println(response);

                        // If redirect intercept intermediate response.
                        if (super.isRedirected(request, response, context)){
                            int statusCode  = response.getStatusLine().getStatusCode();
                            String redirectURL = response.getFirstHeader("Location").getValue();
                            System.out.println("redirectURL: " + redirectURL);
                            return true;
                        } else {
                            request.removeHeaders("Authorization");
                        }
                        return false;
                    }
                })
                .build();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
        RestTemplate restTemplate = new RestTemplate(factory);
        RequestEntity<Void> request = RequestEntity.get(FCP_CONSTRUCT_LIB_ENDPOINT + packageZipName)
                .accept(MediaType.ALL).header("Authorization", "Bearer "+authorizationTokenResult.getAuthorizationToken()).build();
        var zipResponse = restTemplate.exchange(request, InputStream.class);

         */

        return ResponseEntity.ok().build();
    }

    private MappingJackson2HttpMessageConverter jacksonSupportsMoreTypes() {//eg. Gitlab returns JSON as plain text
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList( MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
        return converter;
    }
}
