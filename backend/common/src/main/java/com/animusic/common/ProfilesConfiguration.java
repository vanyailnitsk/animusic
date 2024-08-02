package com.animusic.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
        @PropertySource("classpath:application-${spring.profiles.active}.properties"),
        @PropertySource("classpath:application.properties")
})
public class ProfilesConfiguration {

}