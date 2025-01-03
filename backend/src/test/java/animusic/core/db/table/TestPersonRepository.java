package animusic.core.db.table;

import jakarta.persistence.EntityManager;
import lombok.NonNull;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Component;

import animusic.core.db.model.TestPerson;

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
