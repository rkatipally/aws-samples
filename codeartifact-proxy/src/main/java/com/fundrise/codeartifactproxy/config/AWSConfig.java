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
        AWSSessionCredentials credentials = new BasicSessionCredentials("","", "");
        return AWSCodeArtifactClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }

}
