package com.animusic.core.db.table;

import com.animusic.core.db.model.TestPerson;
import jakarta.persistence.EntityManager;
import lombok.NonNull;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Component;

@Component
@NoRepositoryBean
public interface TestPersonRepository extends CrudRepository<TestPerson, Integer> {

    class Impl extends RepositoryBase<TestPerson, Integer> implements TestPersonRepository {

        public Impl(
                @NonNull EntityManager entityManager
        ) {
            super(entityManager, TestPerson.class);
        }
    }
}
