package com.animusic.content;

import com.animusic.core.conf.DatabaseConfig;
import com.animusic.s3.S3Service;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("testing")
@Import(DatabaseConfig.class)
public class IntegrationTestsConfig {

    @Bean
    public S3ServiceTestImpl s3Service() {
        return new S3ServiceTestImpl();
    }
}
