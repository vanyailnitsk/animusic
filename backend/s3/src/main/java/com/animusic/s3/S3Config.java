package com.animusic.s3;

import java.net.URI;

import com.animusic.common.PropertiesConfig;
import com.animusic.common.S3Properties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;

@Configuration
@Import(PropertiesConfig.class)
@Getter
@RequiredArgsConstructor
@Slf4j
public class S3Config {

    private final S3Properties properties;

    @Bean
    public S3Client s3Client() {
        AwsCredentials credentials = AwsBasicCredentials.create(
                properties.getAccessKey(),
                properties.getSecretKey()
        );
        S3Client client = S3Client.builder()
                .forcePathStyle(true)
                .credentialsProvider(() -> credentials)
                .endpointOverride(URI.create(properties.getUrl()))
                .region(Region.of(properties.getRegion()))
                .build();
        log.info("Found S3 buckets: {}",
                client.listBuckets().buckets());
        return client;
    }

    @Bean
    public S3Service s3Service(S3Client s3Client) {
        return new S3Service(s3Client, properties);
    }
}
