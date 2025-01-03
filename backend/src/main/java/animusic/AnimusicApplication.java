package animusic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import animusic.conf.ContentConfig;

@SpringBootApplication
@Import({ContentConfig.class})
public class AnimusicApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnimusicApplication.class, args);
    }

}

