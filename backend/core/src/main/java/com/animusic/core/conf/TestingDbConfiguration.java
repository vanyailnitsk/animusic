package com.animusic.core.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application-testing.properties")
@Import({DatabaseConfig.class})
@Profile("testing")
public class TestingDbConfiguration {
}
