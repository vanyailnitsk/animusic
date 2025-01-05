package animusic.core.db.table;

import jakarta.persistence.EntityManager;
import lombok.NonNull;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import animusic.core.db.model.PlaylistSoundtrack;

@Component
@NoRepositoryBean
public interface PlaylistSoundtrackRepository extends CrudRepository<PlaylistSoundtrack, Long> {

    void deleteTrackFromPlaylist(Integer playlist_id, Integer soundtrack_id);

    class Impl extends RepositoryBase<PlaylistSoundtrack, Long> implements PlaylistSoundtrackRepository {

        public Impl(@NonNull EntityManager entityManager) {
            super(entityManager, PlaylistSoundtrack.class);
        }

        @Override
        @Transactional
        public void deleteTrackFromPlaylist(Integer playlist_id, Integer soundtrack_id) {
            var query = "DELETE FROM PlaylistSoundtrack s where s.playlist.id = :p_id and s.soundtrack.id = :s_id";
            entityManager.createQuery(query)
                    .setParameter("p_id", playlist_id)
                    .setParameter("s_id", soundtrack_id)
                    .executeUpdate();
        }
    }
}
