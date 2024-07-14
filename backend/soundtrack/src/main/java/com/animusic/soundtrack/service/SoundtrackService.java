package com.animusic.soundtrack.service;

import java.io.IOException;
import java.util.Objects;

import com.animusic.album.service.AlbumService;
import com.animusic.core.InvalidDataException;
import com.animusic.core.JsonMergePatchService;
import com.animusic.core.db.model.Album;
import com.animusic.core.db.model.Anime;
import com.animusic.core.db.model.Image;
import com.animusic.core.db.model.Soundtrack;
import com.animusic.core.db.table.SoundtrackRepository;
import com.animusic.image.service.ImageService;
import com.animusic.s3.S3Service;
import com.animusic.soundtrack.SoundtrackNotFoundException;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor
public class SoundtrackService {
    private final SoundtrackRepository soundtrackRepository;
    private final AlbumService albumService;
    private final ImageService imageService;
    private final S3Service s3Service;
    private final JsonMergePatchService jsonMergePatchService;

    public Soundtrack getSoundtrack(Integer id) {
        return soundtrackRepository.findById(id)
                .orElseThrow(() -> new SoundtrackNotFoundException(id));
    }

    @Transactional(timeout = 30)
    public Soundtrack createSoundtrack(
            MultipartFile audio,
            MultipartFile image,
            Soundtrack soundtrack,
            Integer albumId
    ) {
        Album album = albumService.getAlbumById(albumId);
        Anime anime = album.getAnime();
        String fileName = "%s/audio/%s".formatted(anime.getFolderName(), soundtrack.getAnimeTitle());
        String blobKey = createAudio(fileName, audio);
        soundtrack.setAnime(anime);
        soundtrack.setAudioFile(blobKey);
        if (image != null && !image.isEmpty()) {
            Image savedImage = imageService.createSoundtrackImage(anime.getFolderName(), soundtrack.getAnimeTitle(),
                    image);
            soundtrack.setImage(savedImage);
        }
        Soundtrack savedSoundtrack = soundtrackRepository.save(soundtrack);
        soundtrack.setAlbum(album);
        log.info("Soundtrack {} created successfully", blobKey);
        return savedSoundtrack;
    }


    @Transactional
    public Soundtrack setImage(Integer soundtrackId, MultipartFile image) {
        Soundtrack soundtrack = soundtrackRepository.findById(soundtrackId)
                .orElseThrow(() -> new SoundtrackNotFoundException(soundtrackId));
        Image savedImage = imageService.createSoundtrackImage(soundtrack.getAnime().getFolderName(),
                soundtrack.getAnimeTitle(), image);
        soundtrack.setImage(savedImage);
        return soundtrack;
    }

    public void remove(Integer id) {
        Soundtrack soundtrack = soundtrackRepository.findById(id)
                .orElseThrow(() -> new SoundtrackNotFoundException(id));
        s3Service.deleteObject(soundtrack.getAudioFile());
        if (Objects.nonNull(soundtrack.getImage())) {
            imageService.deleteImage(soundtrack.getImage());
        }
        soundtrackRepository.deleteById(id);
        log.info("Soundtrack {} : {} removed successfully", id, soundtrack.getAudioFile());
    }

    @Transactional
    public Soundtrack updateSoundtrack(
            Integer soundtrackId,
            String originalTitle,
            String animeTitle,
            Integer duration
    ) {
        return soundtrackRepository.findById(soundtrackId).map(
                soundtrack -> {
                    soundtrack.setOriginalTitle(originalTitle);
                    soundtrack.setAnimeTitle(animeTitle);
                    soundtrack.setDuration(duration);
                    return soundtrack;
                }
        ).orElseThrow(() -> new SoundtrackNotFoundException(soundtrackId));
    }

    @Transactional
    public Soundtrack updateAudio(MultipartFile audio, Integer soundtrackId) {
        Soundtrack soundtrack = soundtrackRepository.findById(soundtrackId)
                .orElseThrow(() -> new SoundtrackNotFoundException(soundtrackId));
        if (audio.isEmpty()) {
            throw new InvalidDataException("Содержимое аудиофайла не может быть пустым!");
        }
        String fileName = "%s/audio/%s".formatted(soundtrack.getAnime().getFolderName(), soundtrack.getAnimeTitle());
        String blobKey = createAudio(fileName, audio);
        soundtrack.setAudioFile(blobKey);
        return soundtrack;
    }

    public Soundtrack updateSoundtrack(JsonNode patch, Integer soundtrackId) {
        Soundtrack soundtrack = soundtrackRepository.findById(soundtrackId)
                .orElseThrow(() -> new SoundtrackNotFoundException(soundtrackId));
        Soundtrack res = jsonMergePatchService.mergePatch(patch, soundtrack, Soundtrack.class);
        soundtrackRepository.save(res);
        return res;
    }

    public String createAudio(String fileName, MultipartFile content) {
        String extension = s3Service.getFileExtension(content.getOriginalFilename());
        String contentType = switch (extension) {
            case ".ogg" -> "audio/ogg";
            case ".mp3" -> "audio/mpeg";
            default -> "application/octet-stream";
        };
        try {
            return s3Service.createBlob(fileName, content.getBytes(), contentType);
        } catch (IOException e) {
            throw new RuntimeException("Error on saving audiofile to S3", e);
        }
    }
}
