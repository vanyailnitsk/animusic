package animusic.service.analytics;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import animusic.core.db.model.Soundtrack;
import animusic.core.db.table.TrackListeningEventRepository;
import animusic.core.db.views.TrackListeningsStats;
import animusic.service.IntegrationTestBase;
import animusic.service.soundtrack.SoundtrackService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Transactional
class StatisticsServiceTest extends IntegrationTestBase {

    @MockBean
    TrackListeningEventRepository trackListeningEventRepository;

    @MockBean
    SoundtrackService soundtrackService;

    @Autowired
    StatisticsService statisticsService;

    @Test
    void mostPopularTracks() {
        when(trackListeningEventRepository.mostPopularTracks(any()))
                .thenReturn(List.of(
                        new TrackListeningsStats(2, 25),
                        new TrackListeningsStats(1, 10),
                        new TrackListeningsStats(3, 4)
                ));
        var soundtracks = List.of(
                Soundtrack.builder().id(2).originalTitle("track-2").build(),
                Soundtrack.builder().id(1).originalTitle("track-1").build(),
                Soundtrack.builder().id(3).originalTitle("track-3").build()
        );
        when(soundtrackService.findAllByIds(List.of(2, 1, 3)))
                .thenReturn(soundtracks);
        var mostPopular = statisticsService.mostPopularTracks(10);
        assertThat(mostPopular).isEqualTo(soundtracks);

        verify(trackListeningEventRepository).mostPopularTracks(10);
        verify(soundtrackService).findAllByIds(List.of(2, 1, 3));
    }
}