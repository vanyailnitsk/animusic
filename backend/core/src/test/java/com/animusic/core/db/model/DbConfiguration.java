package com.animusic.core.db.model;

import com.animusic.core.db.table.RepositoryBase;
import com.animusic.core.db.table.TestPersonRepository;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DbConfiguration {
    @Bean
    public RepositoryBase repositoryBase(EntityManager entityManager) {
        return new TestPersonRepository.Impl(entityManager);
    }
}
