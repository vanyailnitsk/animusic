package com.animusic.core.db.table;

import com.animusic.core.db.model.CoverArt;
import jakarta.persistence.EntityManager;
import lombok.NonNull;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Component;

@Component
@NoRepositoryBean
public interface CoverArtRepository extends CrudRepository<CoverArt, Integer> {

    class Impl extends RepositoryBase<CoverArt, Integer> implements CoverArtRepository {

        public Impl(@NonNull EntityManager entityManager) {
            super(entityManager, CoverArt.class);
        }
    }
}
