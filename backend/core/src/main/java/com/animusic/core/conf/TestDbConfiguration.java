package com.animusic.core.conf;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@PropertySource("classpath:application-testing.yaml")
@Profile("testing")
@Import({DatabaseConfig.class})
public class TestDbConfiguration {

    @Bean
    public DataSource dataSource() {
        var dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5433/animusic");
        dataSource.setUsername("test");
        dataSource.setPassword("test");
        return dataSource;
    }

}
