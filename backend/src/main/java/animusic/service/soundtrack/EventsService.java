package animusic.service.soundtrack;

import java.util.Date;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import animusic.core.db.model.TrackListeningEvent;
import animusic.core.db.model.User;
import animusic.core.db.table.TrackListeningEventRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventsService {

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

}
