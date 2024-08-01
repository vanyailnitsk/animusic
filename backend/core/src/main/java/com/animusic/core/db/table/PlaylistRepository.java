package com.animusic.core.db.table;

import java.util.Optional;

import com.animusic.core.db.model.Playlist;
import jakarta.persistence.EntityManager;
import lombok.NonNull;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Component;

@Component
@NoRepositoryBean
public interface PlaylistRepository extends CrudRepository<Playlist, Integer> {

    String FAVOURITE_PLAYLIST_NAME = "Favourite tracks";

    Optional<Playlist> getUserFavouritePlaylist(Integer userId);

    class Impl extends RepositoryBase<Playlist, Integer> implements PlaylistRepository {

        public Impl(@NonNull EntityManager entityManager) {
            super(entityManager, Playlist.class);
        }

        @Override
        public Optional<Playlist> getUserFavouritePlaylist(Integer userId) {
            var query = "SELECT p FROM Playlist p WHERE p.user.id = :userId AND p.name = :name";
            return getOptionalResult(
                    entityManager.createQuery(query, Playlist.class)
                            .setParameter("userId", userId)
                            .setParameter("name", FAVOURITE_PLAYLIST_NAME)
            );
        }
    }
}
