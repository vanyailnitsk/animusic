package com.ilnitsk.animusic.services;

import com.ilnitsk.animusic.models.Soundtrack;
import com.ilnitsk.animusic.models.TrackType;
import com.ilnitsk.animusic.repositories.SoundtrackRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
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
public class SoundtrackService {
    private final SoundtrackRepository soundtrackRepository;
    @Value("${audiotracks.directory}")
    private String musicDirectory;

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
                long rangeEnd = rangeStart + 1000000;
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


}
