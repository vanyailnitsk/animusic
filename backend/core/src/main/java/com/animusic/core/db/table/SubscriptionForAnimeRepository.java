package com.animusic.core.db.table;

import java.util.List;

import com.animusic.core.db.model.SubscriptionForAnime;
import jakarta.persistence.EntityManager;
import lombok.NonNull;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Component;

@Component
@NoRepositoryBean
public interface SubscriptionForAnimeRepository extends CrudRepository<SubscriptionForAnime, Integer> {

    List<SubscriptionForAnime> findByUserId(Integer userId);

    Boolean alreadySubscribed(Integer userId, Integer animeId);

    class Impl extends RepositoryBase<SubscriptionForAnime, Integer> implements SubscriptionForAnimeRepository {

        public Impl(@NonNull EntityManager entityManager) {
            super(entityManager, SubscriptionForAnime.class);
        }

        @Override
        public List<SubscriptionForAnime> findByUserId(Integer userId) {
            var query = "SELECT s from SubscriptionForAnime s where s.user.id = : userId ORDER BY s.id desc ";
            return entityManager.createQuery(query, SubscriptionForAnime.class)
                    .setParameter("userId", userId)
                    .getResultList();
        }

        @Override
        public Boolean alreadySubscribed(Integer userId, Integer animeId) {
            var query = """
                    SELECT count(s) > 0 from SubscriptionForAnime s 
                    where s.user.id = :userId AND s.anime.id = :animeId
                    """;
            return entityManager.createQuery(query, Boolean.class)
                    .setParameter("userId", userId)
                    .setParameter("animeId", animeId)
                    .getSingleResult();
        }
    }
}