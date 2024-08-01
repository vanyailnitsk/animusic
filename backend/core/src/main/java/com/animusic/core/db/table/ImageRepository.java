package com.animusic.core.db.table;

import com.animusic.core.db.model.Image;
import jakarta.persistence.EntityManager;
import lombok.NonNull;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Component;

@Component
@NoRepositoryBean
public interface ImageRepository extends CrudRepository<Image, Integer> {

    class Impl extends RepositoryBase<Image, Integer> implements ImageRepository {

        public Impl(@NonNull EntityManager entityManager) {
            super(entityManager, Image.class);
        }
    }
}
