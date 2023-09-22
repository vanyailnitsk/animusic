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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

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

    public ResponseEntity<Resource> getAudioStream(Integer trackId,String range) throws IOException {
        Optional<Soundtrack> soundtrack = soundtrackRepository.findById(trackId);
        if (soundtrack.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Path audioPath = Paths.get(musicDirectory).resolve(soundtrack.get().getPathToFile());

        if (Files.exists(audioPath)) {
            FileSystemResource audioFile = new FileSystemResource(audioPath.toFile());
            HttpHeaders headers = new HttpHeaders();

            // Если запрошено частичное содержимое
            if (range != null) {
                // Устанавливаем заголовок с частичным содержимым
                headers.set(HttpHeaders.ACCEPT_RANGES, "bytes");

                // Получаем размер файла
                long fileSize = audioFile.contentLength();

                // Извлекаем начальный и конечный байты из заголовка Range
                String[] rangeValues = range.split("=")[1].split("-");
                long start = Long.parseLong(rangeValues[0]);
                long end = rangeValues.length > 1 ? Long.parseLong(rangeValues[1]) : fileSize - 1;

                // Устанавливаем заголовки для частичного содержимого
                headers.set(HttpHeaders.CONTENT_LENGTH, String.valueOf(end - start + 1));
                headers.set(HttpHeaders.CONTENT_RANGE, "bytes " + start + "-" + end + "/" + fileSize);
                headers.setContentType(MediaType.parseMediaType("audio/mpeg"));

                // Возвращаем частичное содержимое файла
                return new ResponseEntity<>(audioFile, headers, HttpStatus.PARTIAL_CONTENT);
            }

            // Если не запрошено частичное содержимое
            headers.set(HttpHeaders.CONTENT_TYPE, "audio/mpeg");
            return new ResponseEntity<>(audioFile, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
