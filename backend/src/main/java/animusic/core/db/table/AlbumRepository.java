package animusic.core.db.table;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Component;


import animusic.core.db.model.Album;
import jakarta.persistence.EntityManager;
import lombok.NonNull;

@Component
@NoRepositoryBean
public interface AlbumRepository extends CrudRepository<Album, Integer> {

    boolean existsByNameAndAnimeId(String albumName, Integer animeId);

    class Impl extends RepositoryBase<Album, Integer> implements AlbumRepository {

        public Impl(@NonNull EntityManager entityManager) {
            super(entityManager, Album.class);
        }

        @Override
        public boolean existsByNameAndAnimeId(String albumName, Integer animeId) {
            var query = "SELECT COUNT(p) > 0 FROM Album p WHERE p.name = :albumName AND p.anime.id = :animeId";
            return entityManager.createQuery(query, Boolean.class)
                    .setParameter("albumName", albumName)
                    .setParameter("animeId", animeId)
                    .getSingleResult();
        }
    }
}
