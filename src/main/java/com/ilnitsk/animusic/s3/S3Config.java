package com.ilnitsk.animusic.s3;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;

@Configuration
@ConfigurationProperties(prefix = "timeweb.s3")
@RequiredArgsConstructor
@Data
public class S3Config {

    private String url;
    private String bucket;

    @Bean
    public S3Client s3Client() {
        S3Client client = S3Client.builder()
                .endpointOverride(URI.create(url))
                .region(Region.of("ru-1"))
                .build();
        return client;
    }
}
