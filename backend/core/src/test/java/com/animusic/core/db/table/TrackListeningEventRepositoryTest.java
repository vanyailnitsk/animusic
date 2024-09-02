package com.animusic.core.db.table;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.animusic.core.db.DatabaseTest;
import com.animusic.core.db.model.TrackListeningEvent;
import com.animusic.core.db.views.TrackListeningsStats;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@Sql
class TrackListeningEventRepositoryTest extends DatabaseTest {

    @Autowired
    TrackListeningEventRepository listeningEventRepository;

    @Autowired
    SoundtrackRepository soundtrackRepository;

    @Test
    void findAll() {
        assertThat(listeningEventRepository.findAll()).isNotNull();
    }

    @Test
    void trackListeningsCount() {
        var soundtrack = soundtrackRepository.findById(1).get();
        assertThat(listeningEventRepository.trackListeningsCount(1)).isEqualTo(0);

        var event1 = TrackListeningEvent.builder()
                .soundtrack(soundtrack)
                .listenedAt(new Date())
                .build();
        var event2 = TrackListeningEvent.builder()
                .soundtrack(soundtrack)
                .listenedAt(new Date())
                .build();
        listeningEventRepository.save(event1);
        listeningEventRepository.save(event2);

        assertThat(listeningEventRepository.trackListeningsCount(1)).isEqualTo(2);
        assertThat(listeningEventRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    void mostPopularTracks() {
        var soundtrack = soundtrackRepository.findById(1).get();
        var event1 = TrackListeningEvent.builder()
                .soundtrack(soundtrack)
                .listenedAt(new Date())
                .build();
        var event2 = TrackListeningEvent.builder()
                .soundtrack(soundtrack)
                .listenedAt(new Date())
                .build();
        listeningEventRepository.saveAll(List.of(event1, event2));
        assertThat(listeningEventRepository.trackListeningsCount(1)).isEqualTo(2);

        var listeningStats = listeningEventRepository.mostPopularTracks(10);
        var idMap = listeningStats.stream()
                .collect(Collectors.toMap(TrackListeningsStats::getTrackId, a -> a));

        assertThat(idMap.get(1).getCount()).isEqualTo(2);
        assertThat(idMap.get(2).getCount()).isEqualTo(1);
    }

    @Test
    void mostPopularAnimeTracks() {
        var soundtrack = soundtrackRepository.findById(1).get();
        var event1 = TrackListeningEvent.builder()
                .soundtrack(soundtrack)
                .listenedAt(new Date())
                .build();
        var event2 = TrackListeningEvent.builder()
                .soundtrack(soundtrack)
                .listenedAt(new Date())
                .build();
        listeningEventRepository.saveAll(List.of(event1, event2));
        assertThat(listeningEventRepository.mostPopularAnimeTracks(1, 10).size()).isEqualTo(2);
    }
}