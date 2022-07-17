package com.fundrise.codeartifactproxy.service;

import com.amazonaws.services.codeartifact.AWSCodeArtifact;
import com.amazonaws.services.codeartifact.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


@Service
@AllArgsConstructor
@Slf4j
public class ProxyService {

    private AWSCodeArtifact awsCodeArtifact;

    private static final String FCP_CONSTRUCT_LIB_ENDPOINT = "https://fundrise-631856996037.d.codeartifact.us-east-1.amazonaws.com/npm/fcp-construct-lib/";

    public ResponseEntity<Object> getPackage(String packageName) throws IOException, InterruptedException {
        /*
        var packageVersionRequest = new ListPackageVersionsRequest();
        packageVersionRequest.withPackage(packageName)
                .withRepository("fcp-construct-lib")
                .withDomain("fundrise")
                .withFormat(PackageFormat.Npm)
                .withOriginType(PackageVersionOriginType.UNKNOWN);
        var packageVersionResult = awsCodeArtifact.listPackageVersions(packageVersionRequest);
        var getRepositoryEndpointRequest = new GetRepositoryEndpointRequest();
        getRepositoryEndpointRequest.withRepository("fcp-construct-lib")
                .withFormat(PackageFormat.Npm)
                .withDomain("fundrise");

         */
//        var repositoryEndpoint = awsCodeArtifact.getRepositoryEndpoint(getRepositoryEndpointRequest).getRepositoryEndpoint();
        var authorizationTokenRequest = new GetAuthorizationTokenRequest();
        authorizationTokenRequest.withDomain("fundrise");
        var authorizationTokenResult = awsCodeArtifact.getAuthorizationToken(authorizationTokenRequest);
//        log.info("Package version result {}", packageVersionResult.getPackage());
//        log.info("Package repository endpoint {}", repositoryEndpoint);
        log.info("Authorization token {}", authorizationTokenResult.getAuthorizationToken());
        var client = HttpClient.newHttpClient();
// create a request
        var request = HttpRequest.newBuilder(
                        URI.create(FCP_CONSTRUCT_LIB_ENDPOINT+packageName))
                .header("accept", "application/json")
                .header("Authorization", "Bearer "+ authorizationTokenResult.getAuthorizationToken())
                .build();

// use the client to send the request
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        var responseBody = new ObjectMapper().readValue(response.body(), Object.class);
        HttpHeaders responseHeaders = new HttpHeaders();
        var responseEntity = ResponseEntity.ok().headers(responseHeaders).body(responseBody);
        log.info("Headers {}", response.headers());
        return responseEntity;
    }
}
