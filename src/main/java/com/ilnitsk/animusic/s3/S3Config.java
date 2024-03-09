package com.ilnitsk.animusic.s3;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;

@Configuration
public class S3Config {

    @Bean
    public S3Client s3Client() {
        S3Client client = S3Client.builder()
                .region(Region.of("ru-1"))
                .build();
        return client;
    }
}
