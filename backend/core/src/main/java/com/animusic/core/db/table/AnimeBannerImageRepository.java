package com.animusic.core.db.table;

import com.animusic.core.db.model.AnimeBannerImage;
import jakarta.persistence.EntityManager;
import lombok.NonNull;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Component;

@Component
@NoRepositoryBean
public interface AnimeBannerImageRepository extends CrudRepository<AnimeBannerImage, Integer> {

    class Impl extends RepositoryBase<AnimeBannerImage, Integer> implements AnimeBannerImageRepository {

        public Impl(@NonNull EntityManager entityManager) {
            super(entityManager, AnimeBannerImage.class);
        }
    }
}
