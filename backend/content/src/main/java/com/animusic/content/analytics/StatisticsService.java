package com.animusic.content.analytics;

import com.animusic.content.soundtrack.SoundtrackService;
import com.animusic.core.db.table.TrackListeningEventRepository;
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
}
