package com.animusic.core.db.table;

import com.animusic.core.db.model.Soundtrack;
import jakarta.persistence.EntityManager;
import lombok.NonNull;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Component;

@Component
@NoRepositoryBean
public interface SoundtrackRepository extends CrudRepository<Soundtrack, Integer> {

    class Impl extends RepositoryBase<Soundtrack, Integer> implements SoundtrackRepository {

        public Impl(@NonNull EntityManager entityManager) {
            super(entityManager, Soundtrack.class);
        }
    }
}
