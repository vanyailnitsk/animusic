package com.animusic.core.db.table;

import java.util.List;
import java.util.Optional;

import com.animusic.core.db.model.Anime;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Component;

@Component
@NoRepositoryBean
public interface AnimeRepository extends CrudRepository<Anime, Integer> {
    Optional<Anime> findAnimeByTitle(String title);

    boolean existsAnimeByTitle(String title);

    List<Anime> findAllOrderByTitle();

    class Impl extends RepositoryBase<Anime, Integer> implements AnimeRepository {

        public Impl(EntityManager entityManager) {
            super(entityManager, Anime.class);
        }

        @Override
        public Optional<Anime> findAnimeByTitle(String title) {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            var query = cb.createQuery(Anime.class);
            var root = query.from(Anime.class);
            query.select(root)
                    .where(cb.equal(root.get("title"), title));
            return getOptionalResult(entityManager.createQuery(query));
        }

        @Override
        public boolean existsAnimeByTitle(String title) {
            return entityManager
                    .createQuery("SELECT COUNT(*) > 0 FROM Anime a WHERE a.title = :title", Boolean.class)
                    .setParameter("title", title)
                    .getSingleResult();
        }

        @Override
        public List<Anime> findAllOrderByTitle() {
            var query = getQuery(Sort.Order.asc("title"));
            return query.getResultList();
        }
    }
}
