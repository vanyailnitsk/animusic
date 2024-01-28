package com.ilnitsk.animusic.soundtrack;

import com.ilnitsk.animusic.playlist.PlaylistNotFoundException;
import com.ilnitsk.animusic.anime.Anime;
import com.ilnitsk.animusic.playlist.Playlist;
import com.ilnitsk.animusic.models.TrackType;
import com.ilnitsk.animusic.anime.AnimeRepository;
import com.ilnitsk.animusic.playlist.PlaylistRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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

    @Autowired
    public SoundtrackService(SoundtrackRepository soundtrackRepository, AnimeRepository animeRepository, PlaylistRepository playlistRepository) {
        this.soundtrackRepository = soundtrackRepository;
        this.animeRepository = animeRepository;
        this.playlistRepository = playlistRepository;
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

    public ResponseEntity<StreamingResponseBody> getAudioStream(Integer trackId, HttpRange range) throws IOException {
        Optional<Soundtrack> soundtrack = soundtrackRepository.findById(trackId);
        if (soundtrack.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Path audioPath = Paths.get(musicDirectory).resolve(soundtrack.get().getPathToFile());
        if (Files.exists(audioPath)) {
            FileSystemResource audioFile = new FileSystemResource(audioPath.toFile());
            HttpHeaders headers = new HttpHeaders();
            StreamingResponseBody stream;
            if (range != null) {
                headers.set(HttpHeaders.ACCEPT_RANGES, "bytes");
                long fileSize = audioFile.contentLength();
                long rangeStart = range.getRangeStart(0);
                long rangeEnd = rangeStart + 2000000;
                if (rangeEnd >= fileSize) {
                    rangeEnd = fileSize - 1;
                }
                long finalRangeEnd = rangeEnd;
                stream = out -> {
                    try (InputStream inputStream = Files.newInputStream(audioPath)) {
                        inputStream.skip(rangeStart);
                        byte[] bytes = inputStream.readNBytes((int) ((finalRangeEnd - rangeStart) + 1));
                        out.write(bytes);
                    } catch (IOException e) {
                        System.out.println("Error with audiofile stream");
                    }
                };
                headers.set(HttpHeaders.CONTENT_LENGTH, String.valueOf(rangeEnd - rangeStart + 1));
                headers.set(HttpHeaders.CONTENT_RANGE, "bytes " + rangeStart + "-" + rangeEnd + "/" + fileSize);
                headers.setContentType(MediaType.parseMediaType("audio/mpeg"));
                return new ResponseEntity<>(stream, headers, HttpStatus.PARTIAL_CONTENT);
            }
            stream = out -> {
                try (InputStream inputStream = Files.newInputStream(audioPath)) {
                    byte[] bytes = inputStream.readAllBytes();
                    out.write(bytes);
                } catch (IOException e) {
                    System.out.println("Error with audiofile stream");
                }
            };
            headers.set(HttpHeaders.CONTENT_TYPE, "audio/mpeg");
            return new ResponseEntity<>(stream, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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

    public void remove(Integer id) throws IOException{
        Soundtrack soundtrack = soundtrackRepository.findById(id)
                        .orElseThrow(() -> new SoundtrackNotFoundException(id));
        File file = new File(musicDirectory,soundtrack.getPathToFile());
        Files.deleteIfExists(file.toPath());
        soundtrackRepository.deleteById(id);
    }
}
