package animusic.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

import animusic.conf.DatabaseConfig;

@Configuration
@Profile("testing")
@Import(DatabaseConfig.class)
public class IntegrationTestsConfig {

    @Bean
    public S3ServiceTestImpl s3Service() {
        return new S3ServiceTestImpl();
    }
}
