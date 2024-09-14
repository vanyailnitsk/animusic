package com.animusic.core.db.table;

import java.util.List;

import com.animusic.core.db.model.SubscriptionForAlbum;
import jakarta.persistence.EntityManager;
import lombok.NonNull;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Component;

@Component
@NoRepositoryBean
public interface SubscriptionForAlbumRepository extends CrudRepository<SubscriptionForAlbum, Integer> {

    List<SubscriptionForAlbum> findByUserId(Integer userId);

    class Impl extends RepositoryBase<SubscriptionForAlbum, Integer> implements SubscriptionForAlbumRepository {

        public Impl(@NonNull EntityManager entityManager) {
            super(entityManager, SubscriptionForAlbum.class);
        }

        @Override
        public List<SubscriptionForAlbum> findByUserId(Integer userId) {
            var query = "SELECT s from SubscriptionForAlbum s where s.user.id = : userId ORDER BY s.id desc ";
            return entityManager.createQuery(query, SubscriptionForAlbum.class)
                    .setParameter("userId", userId)
                    .getResultList();
        }
    }
}