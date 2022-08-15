package com.fundrise.codeartifactproxy.config;

import com.amazonaws.auth.AWSSessionCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.services.codeartifact.AWSCodeArtifact;
import com.amazonaws.services.codeartifact.AWSCodeArtifactClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AWSConfig {

    @Bean
    public AWSCodeArtifact codeArtifactClient(){
        AWSSessionCredentials credentials = new BasicSessionCredentials("ASIAZGHM4T3CWR747IPX", "uJFO1TuxSRSFBH85mKuXZV7jVH391tHsK3d523LP", "IQoJb3JpZ2luX2VjEFwaCXVzLWVhc3QtMSJIMEYCIQCcxRo16k7PlKwTs4hZ32ojiBkOC3kUTjiYKlSrOS9PKQIhANvaZkBwtF4GXg9Jbf5LE84t5AzsFxuwQC6L/5iiuurrKoYDCMX//////////wEQARoMNjMxODU2OTk2MDM3IgzNspK8yfSX2QA5q0cq2gLv6jWoZjGYntsEuSwbkYXZzfi+JqIXU/eQxdDReqV8s+yA5gjwcYVDvcsW3tFXAG3tuvGG3OSQPD0l5E9+z5HdVZ48fsiKYgdQ7XcvPl423gYnly3lmS4cuSSxh86FlyeAu5iH+y0ymUXx+ggEPrwy9y44IxyCLLu+gSWkxus0tJvBq1B09t7iwhoFS93dnpXuGlMx4fJsqltNXlg5YH/babUqX0V8D/nHakOZsM0DA+JNyC5tKMjLFt16mZTN9L1RMDVnms7Ry+p5Iq2cKHTeoVqjwIVEgKInZeQzVeYntCbQTGqGSUANexAVIPMmQ7yYr6sXwFN0/CwwLB2LoKwgR97ISCMmYt5Zy7KPPTFjM4inI/roeivdXktu6hedr5AuMM2rMPUo4SZFnfWqVC4/b/rZPsuqM04HMYa8BkblXVIeItaHaG9O5CJQza0jcevDmbbFEojuw+VbMMS96pcGOqUBkPqDdNNUKF4JcM3UPLUWxmO9IuEdugizOsBkiuBkUE1RnzIu9gN8y75yS8/y6/eV9xsgwRUNjCHBiGP3TgWBtpjmy/xe+BXn74fl8IzBi1L4qiKkh7Kzz6tsI8xfe11mlZxRu0x52kLISqmXZ3xU8iwhNDJte4EwzKEaZ9yWynivIhh7rGc+6kc/JxyDWOfv9Xy37XgzHACNgbx3c5C4gAb6takp");
        return AWSCodeArtifactClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }

}
