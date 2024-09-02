package com.animusic.content.analytics;

import java.util.List;

import com.animusic.content.soundtrack.SoundtrackService;
import com.animusic.core.db.model.Soundtrack;
import com.animusic.core.db.table.TrackListeningEventRepository;
import com.animusic.core.db.views.TrackListeningsStats;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class StatisticsService {

    private final TrackListeningEventRepository listeningEventRepository;

    private final SoundtrackService soundtrackService;

    public Long trackListeningsCount(Integer trackId) {
        soundtrackService.getSoundtrackOrThrow(trackId);
        return listeningEventRepository.trackListeningsCount(trackId);
    }

    public List<Soundtrack> mostPopularTracks(Integer limit) {
        var stats = listeningEventRepository.mostPopularTracks(limit);
        var soundtrackIds = stats.stream()
                .map(TrackListeningsStats::getTrackId)
                .toList();
        return soundtrackService.findAllByIds(soundtrackIds);
    }

    public List<Soundtrack> mostPopularAnimeTracks(Integer animeId, Integer limit) {
        var stats = listeningEventRepository.mostPopularAnimeTracks(animeId, limit);
        var soundtrackIds = stats.stream()
                .map(TrackListeningsStats::getTrackId)
                .toList();
        return soundtrackService.findAllByIds(soundtrackIds);
    }
}
