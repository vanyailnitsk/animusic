package com.ilnitsk.animusic.services;

import com.ilnitsk.animusic.models.Anime;
import com.ilnitsk.animusic.models.Soundtrack;
import com.ilnitsk.animusic.models.TrackType;
import com.ilnitsk.animusic.repositories.AnimeRepository;
import com.ilnitsk.animusic.repositories.SoundtrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

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
public class SoundtrackService {
    private final SoundtrackRepository soundtrackRepository;
    @Value("${audiotracks.directory}")
    private String musicDirectory;
    private final AnimeRepository animeRepository;

    @Autowired
    public SoundtrackService(SoundtrackRepository soundtrackRepository, AnimeRepository animeRepository) {
        this.soundtrackRepository = soundtrackRepository;
        this.animeRepository = animeRepository;
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
    public Soundtrack createFromFile(MultipartFile file, Soundtrack soundtrack, String animeTitle) {
        Anime anime = animeRepository.findAnimeByTitle(animeTitle);
        Path path = Paths.get(musicDirectory,anime.getFolderName());
        int statusCode = Downloader.downloadAudioFromFile(file,path,soundtrack.getAnimeTitle());
        return saveSoundtrack(soundtrack, anime, statusCode);
    }
    public Soundtrack createFromYoutube(String url,Soundtrack soundtrack,String animeTitle) {
        Anime anime = animeRepository.findAnimeByTitle(animeTitle);
        Path path = Paths.get(musicDirectory,anime.getFolderName());
        int statusCode = Downloader.downloadAudioFromUrl(url,path,soundtrack.getAnimeTitle());
        return saveSoundtrack(soundtrack, anime, statusCode);
    }

    private Soundtrack saveSoundtrack(Soundtrack soundtrack, Anime anime, int statusCode) {
        if (statusCode == 0) {
            soundtrack.setAnime(anime);
            soundtrack.setPathToFile(anime.getFolderName()+"/"+soundtrack.getAnimeTitle()+".mp3");
            Soundtrack savedSoundtrack = soundtrackRepository.save(soundtrack);
            anime.getSoundtracks().add(savedSoundtrack);
            animeRepository.save(anime);
            return soundtrackRepository.save(savedSoundtrack);
        }
        else {
            throw new IllegalStateException("Error while downloading audio!");
        }
    }


}
