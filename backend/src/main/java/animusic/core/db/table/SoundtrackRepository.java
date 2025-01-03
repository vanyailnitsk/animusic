package animusic.core.db.table;

import jakarta.persistence.EntityManager;
import lombok.NonNull;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Component;

import animusic.core.db.model.Soundtrack;

@Component
@NoRepositoryBean
public interface SoundtrackRepository extends CrudRepository<Soundtrack, Integer> {

    class Impl extends RepositoryBase<Soundtrack, Integer> implements SoundtrackRepository {

        public Impl(@NonNull EntityManager entityManager) {
            super(entityManager, Soundtrack.class);
        }
    }
}
