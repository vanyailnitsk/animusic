package com.animusic.core.db.table;

import com.animusic.core.db.model.Playlist;
import jakarta.persistence.EntityManager;
import lombok.NonNull;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Component;

@Component
@NoRepositoryBean
public interface PlaylistRepository extends CrudRepository<Playlist, Integer> {
    class Impl extends RepositoryBase<Playlist, Integer> implements PlaylistRepository {

        public Impl(@NonNull EntityManager entityManager) {
            super(entityManager, Playlist.class);
        }
    }
}
