package com.example.sigo.standard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;

@Configuration
public class SNSConfig {

    @Bean
    public SnsClient createClient() {
        return SnsClient.builder()
                .region(Region.US_EAST_1)
                .build();
    }
}
