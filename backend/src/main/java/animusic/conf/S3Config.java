package animusic.conf;

import java.net.URI;
import java.util.Objects;

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

import animusic.conf.properties.PropertiesConfig;
import animusic.conf.properties.S3Properties;
import animusic.service.s3.S3Service;
import animusic.service.s3.S3ServiceImpl;
import animusic.util.StoragePathResolver;

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
        var publicUrl = Objects.requireNonNullElse(properties.getPublicUrl(), properties.getUrl());
        StoragePathResolver.setStorageUrl(properties.getUrl(), properties.getBucket());
        StoragePathResolver.setPublicUrl(publicUrl, properties.getBucket());
        return new S3ServiceImpl(s3Client, properties);
    }
}
