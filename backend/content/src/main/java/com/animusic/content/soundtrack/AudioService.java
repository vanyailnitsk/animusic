package com.animusic.content.soundtrack;

import java.io.IOException;

import com.animusic.core.InvalidDataException;
import com.animusic.core.db.model.Soundtrack;
import com.animusic.s3.S3Service;
import com.animusic.s3.StoragePathResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class AudioService {

    private final S3Service s3Service;

    public String createAudioFile(Soundtrack soundtrack, MultipartFile audio) {
        if (audio.isEmpty()) {
            throw new InvalidDataException("Содержимое аудиофайла не может быть пустым!");
        }
        var animeFolder = soundtrack.getAnime().getFolderName();
        var fileName = StoragePathResolver.soundtrackAudioFile(
                animeFolder,
                soundtrack.getAnimeTitle(),
                audio.getOriginalFilename()
        );
        var extension = StoragePathResolver.getFileExtension(fileName);
        var contentType = switch (extension) {
            case ".ogg" -> "audio/ogg";
            case ".mp3" -> "audio/mpeg";
            case ".aac" -> "audio/aac";
            default -> "application/octet-stream";
        };
        try {
            return s3Service.createBlob(fileName, audio.getBytes(), contentType);
        } catch (IOException e) {
            throw new RuntimeException("Error on saving audiofile to S3", e);
        }
    }
}
