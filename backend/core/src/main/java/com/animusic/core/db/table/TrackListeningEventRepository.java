package com.animusic.core.db.table;

import com.animusic.core.db.model.TrackListeningEvent;
import jakarta.persistence.EntityManager;
import lombok.NonNull;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Component;

@Component
@NoRepositoryBean
public interface TrackListeningEventRepository extends CrudRepository<TrackListeningEvent, Integer> {

    class Impl extends RepositoryBase<TrackListeningEvent, Integer> implements TrackListeningEventRepository {

        public Impl(@NonNull EntityManager entityManager) {
            super(entityManager, TrackListeningEvent.class);
        }
    }
}
