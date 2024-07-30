package com.animusic.core.db.table;

import com.animusic.core.AnimusicApplication;
import com.animusic.core.conf.DatabaseConfig;
import com.animusic.core.db.model.TestPerson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig({
        AnimusicApplication.class,
        DatabaseConfig.class
})
@Sql(scripts = "RepositoryBaseTest.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = "cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
class RepositoryBaseTest {
    @Autowired
    TestPersonRepository testPersonRepository;

    @Test
    void save() {
        TestPerson person = new TestPerson("name", "email");
        testPersonRepository.save(person);
        assertThat(person.getId()).isNotNull();
        System.out.println(testPersonRepository.findAll());
    }

    @Test
    void saveAll() {
    }

    @Test
    void findById() {
    }

    @Test
    void existsById() {
    }

    @Test
    void findAll() {
    }

    @Test
    void testFindAll() {
    }

    @Test
    void findAllById() {
    }

    @Test
    void count() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void delete() {
    }

    @Test
    void deleteAllById() {
    }

    @Test
    void deleteAll() {
    }

    @Test
    void testDeleteAll() {
    }

    @Test
    void getQuery() {
    }

    @Test
    void toJpaOrder() {
    }

    @Test
    void getOptionalResult() {
    }
}