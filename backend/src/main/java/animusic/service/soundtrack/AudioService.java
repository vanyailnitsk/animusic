package animusic.service.soundtrack;

import java.io.IOException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import animusic.core.InvalidDataException;
import animusic.core.db.model.Soundtrack;
import animusic.service.s3.S3Service;
import animusic.util.StoragePathResolver;

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
            case ".m4a" -> "audio/mp4";
            default -> "application/octet-stream";
        };
        try {
            return s3Service.createBlob(fileName, audio.getBytes(), contentType);
        } catch (IOException e) {
            throw new RuntimeException("Error on saving audiofile to S3", e);
        }
    }
}
