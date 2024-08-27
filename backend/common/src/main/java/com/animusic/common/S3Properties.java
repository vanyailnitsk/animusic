package com.animusic.common;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "timeweb.s3")
@Data
@Slf4j
public class S3Properties {

    private String url;

    private String publicUrl;

    private String bucket;

    private String region;

    @Value("access-key")
    private String accessKey;

    @Value("secret-key")
    private String secretKey;

    @PostConstruct
    public void init() {
        log.info("Using S3 properties: {}", this);
    }

}
