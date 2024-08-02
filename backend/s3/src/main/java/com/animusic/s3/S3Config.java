package com.animusic.s3;

import java.net.URI;

import com.animusic.common.PropertiesConfig;
import com.animusic.common.S3Properties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
@Import(PropertiesConfig.class)
@Getter
@RequiredArgsConstructor
public class S3Config {

    private final S3Properties properties;

    @Bean
    public S3Client s3Client() {
        AwsCredentials credentials = AwsBasicCredentials.create(
                properties.getAccessKey(),
                properties.getSecretKey()
        );
        S3Client client = S3Client.builder()
                .credentialsProvider(() -> credentials)
                .endpointOverride(URI.create(properties.getUrl()))
                .region(Region.of(properties.getRegion()))
                .build();
        return client;
    }
}
