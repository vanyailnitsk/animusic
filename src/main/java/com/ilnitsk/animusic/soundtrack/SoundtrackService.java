package com.ilnitsk.animusic.soundtrack;

import com.ilnitsk.animusic.anime.Anime;
import com.ilnitsk.animusic.anime.AnimeRepository;
import com.ilnitsk.animusic.exception.PlaylistNotFoundException;
import com.ilnitsk.animusic.exception.SoundtrackNotFoundException;
import com.ilnitsk.animusic.file.FileService;
import com.ilnitsk.animusic.playlist.Playlist;
import com.ilnitsk.animusic.playlist.PlaylistRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SoundtrackService {
    private final SoundtrackRepository soundtrackRepository;
    @Value("${audiotracks.directory}")
    private String musicDirectory;
    private final AnimeRepository animeRepository;
    private final PlaylistRepository playlistRepository;
    private final FileService fileService;

    public SoundtrackService(SoundtrackRepository soundtrackRepository, AnimeRepository animeRepository, PlaylistRepository playlistRepository, FileService fileService) {
        this.soundtrackRepository = soundtrackRepository;
        this.animeRepository = animeRepository;
        this.playlistRepository = playlistRepository;
        this.fileService = fileService;
    }

    public Soundtrack getSoundtrack(Integer id) {
        return soundtrackRepository.findById(id)
                .orElseThrow(() -> new SoundtrackNotFoundException(id));
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

    public ResponseEntity<StreamingResponseBody> getAudioStream(Integer trackId, HttpRange range) {
        Optional<Soundtrack> soundtrack = soundtrackRepository.findById(trackId);
        if (soundtrack.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        String animeFolder = "Attack_on_Titan";
        String trackName = "Vogel im Käfig.mp3";
        HttpHeaders headers = new HttpHeaders();
        StreamingResponseBody stream;
        if (range != null) {
            headers.set(HttpHeaders.ACCEPT_RANGES, "bytes");
            int fileSize = fileService.getAudioFileSize(animeFolder, trackName);
            int rangeStart = (int) range.getRangeStart(0);
            int rangeEnd = rangeStart + 2000000;
            if (rangeEnd >= fileSize) {
                rangeEnd = fileSize - 1;
            }
            byte[] audioContent = fileService.getAudioContent(animeFolder, trackName, rangeStart, rangeEnd);
            stream = out -> {
                out.write(audioContent);
            };
            headers.set(HttpHeaders.CONTENT_LENGTH, String.valueOf(rangeEnd - rangeStart + 1));
            headers.set(HttpHeaders.CONTENT_RANGE, "bytes " + rangeStart + "-" + rangeEnd + "/" + fileSize);
            headers.setContentType(MediaType.parseMediaType("audio/mpeg"));
            return new ResponseEntity<>(stream, headers, HttpStatus.PARTIAL_CONTENT);
        }
        stream = out -> {
            int fileSize = fileService.getAudioFileSize(animeFolder, trackName);
            out.write(fileService.getAudioContent(animeFolder, trackName, 0, fileSize - 1));
        };
        headers.set(HttpHeaders.CONTENT_TYPE, "audio/mpeg");
        return new ResponseEntity<>(stream, headers, HttpStatus.OK);
    }

    @Transactional
    public Soundtrack createSoundtrack(Object audioSource, Soundtrack soundtrack, Integer playlistId) {
        Optional<Playlist> playlistEntity = playlistRepository.findById(playlistId);
        if (playlistEntity.isEmpty()) {
            throw new PlaylistNotFoundException(playlistId);
        }
        Playlist playlist = playlistEntity.get();
        Anime anime = playlist.getAnime();
        Path path = Paths.get(musicDirectory, anime.getFolderName());
        SoundtrackDownloader.downloadAudio(audioSource, path, soundtrack.getAnimeTitle());
        soundtrack.setAnime(anime);
        soundtrack.setPathToFile(anime.getFolderName() + "/" + soundtrack.getAnimeTitle() + ".mp3");
        Soundtrack savedSoundtrack = soundtrackRepository.save(soundtrack);
        playlist.addSoundtrack(soundtrack);
        playlistRepository.save(playlist);
        log.info("Soundtrack {} created successfully", soundtrack.getAnimeTitle());
        return soundtrackRepository.save(savedSoundtrack);
    }

    public void remove(Integer id) throws IOException {
        Soundtrack soundtrack = soundtrackRepository.findById(id)
                .orElseThrow(() -> new SoundtrackNotFoundException(id));
        File file = new File(musicDirectory, soundtrack.getPathToFile());
        Files.deleteIfExists(file.toPath());
        soundtrackRepository.deleteById(id);
    }
}
