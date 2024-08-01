package com.animusic.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application-testing.properties")
@Profile("testing")
public class TestingDbConfiguration {
}
