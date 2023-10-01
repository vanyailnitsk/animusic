package com.ilnitsk.animusic.services;

import com.ilnitsk.animusic.models.Anime;
import com.ilnitsk.animusic.models.Playlist;
import com.ilnitsk.animusic.models.Soundtrack;
import com.ilnitsk.animusic.models.TrackType;
import com.ilnitsk.animusic.repositories.AnimeRepository;
import com.ilnitsk.animusic.repositories.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PlaylistService {
    private final AnimeRepository animeRepository;
    private final PlaylistRepository playlistRepository;

    @Autowired
    public PlaylistService(AnimeRepository animeRepository, PlaylistRepository playlistRepository) {
        this.animeRepository = animeRepository;
        this.playlistRepository = playlistRepository;
    }

    public void init() {
        List<Anime> animeList = animeRepository.findAll();
        for (Anime anime : animeList) {
            List<Soundtrack> soundtracks = anime.getSoundtracks();
            Map<TrackType, List<Soundtrack>> groupedSoundtracks = soundtracks.stream()
                    .collect(Collectors.groupingBy(Soundtrack::getType));
            for (Map.Entry<TrackType, List<Soundtrack>> entry : groupedSoundtracks.entrySet()) {
                TrackType trackType = entry.getKey();
                List<Soundtrack> soundtracksOfType = entry.getValue();

                Playlist playlist = Playlist.builder()
                        .name(trackType.toString())
                        .imageUrl("/")
                        .anime(anime)
                        .soundtracks(soundtracksOfType)
                        .build();
                playlistRepository.save(playlist);
            }
        }
    }
}

