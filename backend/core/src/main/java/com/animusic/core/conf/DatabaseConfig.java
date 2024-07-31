package com.animusic.core.conf;

import javax.sql.DataSource;

import com.animusic.core.db.table.AnimeRepository;
import jakarta.persistence.EntityManager;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.animusic.core.db.table")
@EnableTransactionManagement
public class DatabaseConfig {

    @Bean
    public SpringLiquibase liquibase(DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:liquibase/changelog.xml");
        liquibase.setDataSource(dataSource);
        return liquibase;
    }

    @Bean
    public AnimeRepository animeRepository(EntityManager entityManager) {
        return new AnimeRepository.Impl(entityManager);
    }
}
