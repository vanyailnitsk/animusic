package animusic.core.db.table;

import jakarta.persistence.EntityManager;
import lombok.NonNull;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Component;

import animusic.core.db.model.Image;

@Component
@NoRepositoryBean
public interface ImageRepository extends CrudRepository<Image, Integer> {

    class Impl extends RepositoryBase<Image, Integer> implements ImageRepository {

        public Impl(@NonNull EntityManager entityManager) {
            super(entityManager, Image.class);
        }
    }
}
