package animusic.core.db;

import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import animusic.conf.DatabaseConfig;
import animusic.core.db.table.TestPersonRepository;

@Configuration
@Import({DatabaseConfig.class})
public class TestingDbConfig {

    @Bean
    public TestPersonRepository testPersonRepository(EntityManager entityManager) {
        return new TestPersonRepository.Impl(entityManager);
    }
}
