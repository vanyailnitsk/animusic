package com.ilnitsk.animusic.services;

import com.ilnitsk.animusic.models.Soundtrack;
import com.ilnitsk.animusic.models.TrackType;
import com.ilnitsk.animusic.repositories.SoundtrackRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SoundtrackService {
    private final SoundtrackRepository soundtrackRepository;

    @Autowired
    public SoundtrackService(SoundtrackRepository soundtrackRepository) {
        this.soundtrackRepository = soundtrackRepository;
    }

    public List<Soundtrack> getTypedSoundtrackList(List<Soundtrack> soundtracks) {
        List<Soundtrack> sortedList = new ArrayList<>();
        soundtracks.stream()
                .filter(s -> s.getType() == TrackType.OPENING)
                .sorted(Comparator.comparingInt(
                        a -> Integer.parseInt(a.getAnimeTitle().split(" ")[1]))
                ).collect(Collectors.toCollection(() -> sortedList));
        soundtracks.stream()
                .filter(s -> s.getType() == TrackType.ENDING)
                .sorted(Comparator.comparingInt(
                        a -> Integer.parseInt(a.getAnimeTitle().split(" ")[1]))
                ).collect(Collectors.toCollection(() -> sortedList));
        soundtracks.stream().filter(s -> s.getType() == TrackType.THEME)
                .collect(Collectors.toCollection(() -> sortedList));
        soundtracks.stream().filter(s -> s.getType() == TrackType.SCENE_SONG)
                .collect(Collectors.toCollection(() -> sortedList));
        return sortedList;
    }


}
