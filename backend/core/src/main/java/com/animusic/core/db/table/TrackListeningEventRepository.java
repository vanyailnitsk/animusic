package com.animusic.core.db.table;

import java.util.List;

import com.animusic.core.db.model.TrackListeningEvent;
import com.animusic.core.db.views.TrackListeningsStats;
import jakarta.persistence.EntityManager;
import lombok.NonNull;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@NoRepositoryBean
public interface TrackListeningEventRepository extends CrudRepository<TrackListeningEvent, Integer> {

    Long trackListeningsCount(Integer trackId);

    List<TrackListeningsStats> mostPopularTracks(Integer limit);

    List<TrackListeningsStats> mostPopularAnimeTracks(Integer animeId, Integer limit);

    class Impl extends RepositoryBase<TrackListeningEvent, Integer> implements TrackListeningEventRepository {

        private final NamedParameterJdbcTemplate jdbcTemplate;

        public Impl(@NonNull EntityManager entityManager, NamedParameterJdbcTemplate jdbcTemplate) {
            super(entityManager, TrackListeningEvent.class);
            this.jdbcTemplate = jdbcTemplate;
        }

        @Override
        public Long trackListeningsCount(Integer trackId) {
            var query = "SELECT COUNT(t) FROM TrackListeningEvent t WHERE t.soundtrack.id = :trackId";
            return entityManager.createQuery(query, Long.class)
                    .setParameter("trackId", trackId)
                    .getSingleResult();
        }

        public List<TrackListeningsStats> mostPopularTracks(Integer limit) {
            var query = """
                    select s.id as track_id, count(*) as listenings 
                    from soundtrack s left join track_listening_events tl on tl.track_id=s.id 
                    group by s.id order by listenings desc, s.id limit :limit;
                    """;
            var params = new MapSqlParameterSource();
            params.addValue("limit", limit);
            return jdbcTemplate.query(query, params, (rs, rowNum) -> {
                var track = new TrackListeningsStats(
                        rs.getInt("track_id"),
                        rs.getInt("listenings")
                );
                return track;
            });
        }

        @Override
        public List<TrackListeningsStats> mostPopularAnimeTracks(Integer animeId, Integer limit) {
            var query = """
                    select s.id as track_id, count(*) as listenings 
                    from soundtrack s left join track_listening_events tl on tl.track_id=s.id 
                    where s.anime_id = :animeId
                    group by s.id order by listenings desc, s.id limit :limit ;
                    """;
            var params = new MapSqlParameterSource();
            params.addValue("limit", limit);
            params.addValue("animeId", animeId);
            return jdbcTemplate.query(query, params, (rs, rowNum) -> {
                var track = new TrackListeningsStats(
                        rs.getInt("track_id"),
                        rs.getInt("listenings")
                );
                return track;
            });
        }
    }
}
