package com.animusic.core.db.table;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.animusic.core.db.DatabaseTest;
import com.animusic.core.db.model.TestPerson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@Sql(scripts = "RepositoryBaseTest.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = "cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
class RepositoryBaseTest extends DatabaseTest {

    @Autowired
    RepositoryBase<TestPerson, Integer> testPersonRepository;

    @Test
    void save() {
        TestPerson person = new TestPerson("name", "email");
        testPersonRepository.save(person);
        assertThat(person.getId()).isNotNull();
    }

    @Test
    void saveAll() {
        var persons = List.of(
                new TestPerson("markus", "markus@a.com"),
                new TestPerson("connor", "connor@a.com"),
                new TestPerson("kara", "kara@a.com")
        );
        testPersonRepository.saveAll(persons);
        assertThat(testPersonRepository.findAll().size()).isEqualTo(3);
    }

    @Test
    void findById() {
        var person = new TestPerson("markus", "markus@a.com");
        var saved = testPersonRepository.save(person);
        assertThat(testPersonRepository.findById(saved.getId()).get()).isEqualTo(saved);
    }

    @Test
    void findByIdNotExists() {
        assertThat(testPersonRepository.findById(1)).isEmpty();
    }

    @Test
    void existsById() {
        var person = new TestPerson("markus", "markus@a.com");
        testPersonRepository.save(person);
        assertThat(testPersonRepository.existsById(person.getId())).isTrue();
    }

    @Test
    void notExistsById() {
        assertThat(testPersonRepository.existsById(1)).isFalse();
    }

    @Test
    void findAll() {
        var persons = List.of(
                new TestPerson("markus", "markus@a.com"),
                new TestPerson("connor", "connor@a.com"),
                new TestPerson("kara", "kara@a.com")
        );
        testPersonRepository.saveAll(persons);
        assertThat(testPersonRepository.findAll()).isEqualTo(persons);
    }

    @Test
    void findAllEmpty() {
        assertThat(testPersonRepository.findAll()).isEqualTo(List.of());
    }

    @Test
    void findAllWithCustomOrder() {
        var persons = List.of(
                new TestPerson("markus", "markus@a.com"),
                new TestPerson("connor", "connor@a.com"),
                new TestPerson("kara", "kara@a.com")
        );
        testPersonRepository.saveAll(persons);

        var personsSortedByName = testPersonRepository.findAll(Sort.Order.asc("name"));

        var expected = new ArrayList<>(persons);
        expected.sort(Comparator.comparing(TestPerson::getName));

        assertThat(personsSortedByName).isEqualTo(expected);

    }

    @Test
    void findAllById() {
        var persons = List.of(
                new TestPerson("markus", "markus@a.com"),
                new TestPerson("connor", "connor@a.com"),
                new TestPerson("kara", "kara@a.com")
        );
        testPersonRepository.saveAll(persons);
        var ids = persons.stream().mapToInt(TestPerson::getId).boxed().toList();
        assertThat(testPersonRepository.findAllById(ids)).isEqualTo(persons);
    }

    @Test
    void count() {
        var persons = List.of(
                new TestPerson("markus", "markus@a.com"),
                new TestPerson("connor", "connor@a.com")
        );
        testPersonRepository.saveAll(persons);
        assertThat(testPersonRepository.count()).isEqualTo(2);
    }

    @Test
    void deleteById() {
        var persons = List.of(
                new TestPerson("markus", "markus@a.com"),
                new TestPerson("connor", "connor@a.com"),
                new TestPerson("kara", "kara@a.com")
        );
        testPersonRepository.saveAll(persons);

        var markus = persons.get(0);
        testPersonRepository.deleteById(markus.getId());

        assertThat(testPersonRepository.findById(markus.getId())).isEmpty();
        assertThat(testPersonRepository.findAll()).isEqualTo(List.of(persons.get(1), persons.get(2)));
    }

    @Test
    void delete() {
        var person = new TestPerson("markus", "markus@a.com");

        testPersonRepository.save(person);
        assertThat(testPersonRepository.findById(person.getId())).isNotEmpty();

        testPersonRepository.delete(person);
        assertThat(testPersonRepository.findById(person.getId())).isEmpty();
    }

    @Test
    void deleteAllById() {
        var persons = List.of(
                new TestPerson("markus", "markus@a.com"),
                new TestPerson("connor", "connor@a.com"),
                new TestPerson("kara", "kara@a.com")
        );
        testPersonRepository.saveAll(persons);

        assertThat(testPersonRepository.findAll()).isEqualTo(persons);
        var ids = persons.stream().mapToInt(TestPerson::getId).boxed().toList();
        testPersonRepository.deleteAllById(ids);

        assertThat(testPersonRepository.findAll()).isEqualTo(List.of());
    }

    @Test
    void deleteAll() {
        var persons = List.of(
                new TestPerson("markus", "markus@a.com"),
                new TestPerson("connor", "connor@a.com"),
                new TestPerson("kara", "kara@a.com")
        );
        testPersonRepository.saveAll(persons);

        assertThat(testPersonRepository.findAll()).isEqualTo(persons);

        testPersonRepository.deleteAll();

        assertThat(testPersonRepository.findAll()).isEqualTo(List.of());
    }

    @Test
    void isNew() {
        var person = new TestPerson("markus", "markus@a.com");

        assertThat(testPersonRepository.isNew(person)).isTrue();
        testPersonRepository.save(person);

        assertThat(testPersonRepository.isNew(person)).isFalse();
    }
}