package com.animusic.core.db.table;

import java.util.List;

import com.animusic.core.db.model.Album;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.NonNull;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Component;

@Component
@NoRepositoryBean
public interface AlbumRepository extends CrudRepository<Album, Integer> {

    List<Album> getAlbumsByAnimeId(Integer animeId);

    boolean existsByNameAndAnimeId(String albumName, Integer animeId);

    class Impl extends RepositoryBase<Album, Integer> implements AlbumRepository {

        public Impl(@NonNull EntityManager entityManager) {
            super(entityManager, Album.class);
        }

        @Override
        public List<Album> getAlbumsByAnimeId(Integer animeId) {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            var query = cb.createQuery(Album.class);
            var root = query.from(Album.class);
            query.select(root)
                    .where(cb.equal(root.get("anime_id"), animeId));
            return entityManager.createQuery(query).getResultList();
        }

        @Override
        public boolean existsByNameAndAnimeId(String albumName, Integer animeId) {
            return entityManager.createQuery(
                    "SELECT COUNT(p) > 0 FROM Album p WHERE p.name = :albumName AND p.anime.id = :animeId",
                    Boolean.class
            ).getSingleResult();
        }
    }
}
