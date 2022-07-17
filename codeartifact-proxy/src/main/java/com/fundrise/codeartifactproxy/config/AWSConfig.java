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
        AWSSessionCredentials credentials = new BasicSessionCredentials("ASIAZGHM4T3CR5LDZWLQ", "mcY60JE1tXMq313TOZr1UVAbd3A2MAA/V7CLBFUH", "IQoJb3JpZ2luX2VjEKP//////////wEaCXVzLWVhc3QtMSJHMEUCICvabdZXaN780js9QXrdo4SxO3nmi799V0Ya54mGNyJLAiEAkfVTqyBGbOU6FEmmub/Sk8xZOjoVvA/O4wtLDVVUcT4qhgMI3P//////////ARABGgw2MzE4NTY5OTYwMzciDFQ3FDtLhqFSfePCASraAvq/wzI9qDWftJ5Gz5pf1N/Ho9s5eyghmgwcAviGBU5dJNUxsJjEAuf+Mms/2RhHoDOx5GEF82VWwlgMm3i1MHfxdY/sa2YlNcGjCL1d8wLQpvClPQ9BM2JWjZyLs4bC0mB3lDVTyjJYsyYE1qKGID8qOUff70QB/CAamLLKi9AMc/CH6IJkxnC91L7aGqxkXyEs0TqABGpyUeRDozl6iPBm7Rb4w3BkWrLVy5w/c+Tkj+Q1lO7gn5m2blydAQh1+3iQY/Bhr7+TndKzPM9jNwfCBVPyob152Cy+dkjVx7Z/LrEn0m79/7B/1t0HRTFh8UbOfwm2++0y1QtdX+59y3Izx1r/yvxKlOTzZA/yNbqxSUalEqkta72Nu1Oho9w1LRcxQtfD9G2wgHz9oSd0BZW1ATygZl6FkrpJ8vrz9mVOMfaAOE2RIlyMxQ/bs2J1Wh8OD42fkl2AdsUwgKzRlgY6pgHMMoUowqpyPTkzqknEOgB/rSyMlrgvkmDWxzz4P1wwKm6ssJk29X3VsfyynMkW71vZSsCJNo65tlhbeXaxkPrwgCl6ftQ0Q9rN8I5O48aIdV8IDgpiqVkohfalxsV5nGyJr260zLRcV+2dobNHrILwsk77zoe9HdEnmLQpUpYVSOM6CmImpoEmjQX68WSeseKp6tDv3PxPO4Cmq/9e4ovITSBtiAas");
        return AWSCodeArtifactClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }

}
