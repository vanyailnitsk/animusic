package com.animusic.content.soundtrack;

import com.animusic.core.db.table.SoundtrackRepository;
import com.animusic.core.db.table.TrackListeningEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ListeningStatsService {

    private TrackListeningEventRepository listeningEventRepository;

    private SoundtrackRepository soundtrackRepository;

    public Long trackListeningsCount(Integer trackId) {
        if (soundtrackRepository.findById(trackId).isEmpty()) {
            throw new SoundtrackNotFoundException(trackId);
        }
        return listeningEventRepository.trackListeningsCount(trackId);
    }
}
