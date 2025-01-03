package animusic.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import animusic.conf.ContentConfig;
import animusic.conf.SecurityConfig;

@SpringBootApplication
@Import({ContentConfig.class, SecurityConfig.class})
@ComponentScan(basePackages = {"animusic"})
public class AnimusicApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnimusicApplication.class, args);
    }

}

