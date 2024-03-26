package com.ilnitsk.animusic.s3;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
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
    private String region;
    @Value("access-key")
    private String accessKey;
    @Value("secret-key")
    private String secretKey;

    @Bean
    public S3Client s3Client() {
        AwsCredentials credentials = AwsBasicCredentials.create(accessKey,secretKey);
        S3Client client = S3Client.builder()
                .credentialsProvider(() -> credentials)
                .endpointOverride(URI.create(url))
                .region(Region.of(region))
                .build();
        return client;
    }
}
