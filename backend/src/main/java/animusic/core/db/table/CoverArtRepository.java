package animusic.core.db.table;

import jakarta.persistence.EntityManager;
import lombok.NonNull;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Component;

import animusic.core.db.model.CoverArt;

@Component
@NoRepositoryBean
public interface CoverArtRepository extends CrudRepository<CoverArt, Integer> {

    class Impl extends RepositoryBase<CoverArt, Integer> implements CoverArtRepository {

        public Impl(@NonNull EntityManager entityManager) {
            super(entityManager, CoverArt.class);
        }
    }
}
