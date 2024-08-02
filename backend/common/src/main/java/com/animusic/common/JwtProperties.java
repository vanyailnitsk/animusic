package com.animusic.common;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "token")
@Data
public class JwtProperties {

    private String secret;

    private Long expirationMinutes;

    private Long refreshExpirationHours;
}
