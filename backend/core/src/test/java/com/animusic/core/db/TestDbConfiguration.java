package com.animusic.core.db;

import com.animusic.common.ProfilesConfiguration;
import com.animusic.core.db.table.TestPersonRepository;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ProfilesConfiguration.class})
public class TestDbConfiguration {

    @Bean
    public TestPersonRepository testPersonRepository(EntityManager entityManager) {
        return new TestPersonRepository.Impl(entityManager);
    }
}
