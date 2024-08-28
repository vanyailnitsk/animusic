package com.animusic.content.soundtrack;

import java.util.Date;

import com.animusic.core.db.model.TrackListeningEvent;
import com.animusic.core.db.model.User;
import com.animusic.core.db.table.TrackListeningEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ListeningStatsService {

    private final TrackListeningEventRepository listeningEventRepository;

    private final SoundtrackService soundtrackService;

    public TrackListeningEvent addListeningEvent(User user, Integer soundtrackId) {
        var event = TrackListeningEvent.builder()
                .soundtrack(soundtrackService.getSoundtrackOrThrow(soundtrackId))
                .user(user)
                .listenedAt(new Date())
                .build();
        return listeningEventRepository.save(event);
    }

    public Long trackListeningsCount(Integer trackId) {
        soundtrackService.getSoundtrackOrThrow(trackId);
        return listeningEventRepository.trackListeningsCount(trackId);
    }
}
