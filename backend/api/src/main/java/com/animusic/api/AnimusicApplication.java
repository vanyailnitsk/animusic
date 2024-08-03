package com.animusic.api;

import com.animusic.content.conf.ContentConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({ContentConfig.class})
@ComponentScan(basePackages = {"com.animusic"})
public class AnimusicApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnimusicApplication.class, args);
    }

}

