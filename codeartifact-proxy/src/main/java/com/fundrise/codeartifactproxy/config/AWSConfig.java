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
        AWSSessionCredentials credentials = new BasicSessionCredentials("ASIAZGHM4T3CS4PWYRM4", "YD1sgdFf5mNev5OJc30mQlCxf/ojVkg3U0IVfQnR", "IQoJb3JpZ2luX2VjELX//////////wEaCXVzLWVhc3QtMSJIMEYCIQCg0YvBI38nW7JEUXqs1pz2wgYfjbi3xlGYRJuA/EhAugIhALRepdzBvTrWwvbh4glGB5MTkxiSjiGPm19/9xIWlUCJKv0CCC4QARoMNjMxODU2OTk2MDM3Igz//SJWCRQoAwuE5OAq2gKPjww6IoVRd1vRH7AxzikUvp2Dgcz2a8SOk85SQ13ibtA944qJKtwsys6V4Mh6L7FZRXxoGvCFaaMxZ3i3Hnei0cpPZS8GojyzPejlfyZk7cy2OpF1pg3Zs4+WRqICpKHb6HavvbBsBFVUY2tAZUeSGIlJIrbxYhI77tl93v1rUgYjYP/woiYHF0mOqHP8/v9kFjJtkn7pnWjDbEgQx8pUgc2KrejCISAd+5QtBtxXi5b55DFB2NCIkYY1s9ldPG6Z4nBtjEoWvBkQ4NUEAWKwxJy0VEokYlkB34us0cTvkafgW5mjdKBnhm/7h2afQuZOkkmYsqLroEeA9T4RtmI3i8nSWOSAI3ivXFyLAu3dpKoZGUViBLkmXxZa9RUrNvlwqCsa6LrwTYHOtzGaKmLOQ1Vn9uQomC9jy0sbkNLTNxNefUz06b/k0FrOcg9thOYzut6x4a+vguQmMLyK/pcGOqUBHh5cPcNB5jFYuXJPA2IP9jvj+fXkHCBk+fdtEyItvP8BsqJgD3Obkb/4EVpamx/XefHl4x0eDxaDDK+gl3wXIX9Dk2auZ6Sb4SFu1gqEjtfAecnceEuYos3O0w3PtzQ30oO3YLi0rNXy9ah5aNh+WTFDBEJBTB+coQTias5yD4KnbOkGslrrV5VIi/Cx9UpEG/hymYJzenIbDVoBxsMqcuCyUV3C");
        return AWSCodeArtifactClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }

}
