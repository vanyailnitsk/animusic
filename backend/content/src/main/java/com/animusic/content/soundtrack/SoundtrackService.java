package com.animusic.content.soundtrack;

import java.util.Date;
import java.util.Objects;

import com.animusic.content.JsonMergePatchService;
import com.animusic.content.album.AlbumService;
import com.animusic.content.image.ImageService;
import com.animusic.core.db.model.Album;
import com.animusic.core.db.model.Anime;
import com.animusic.core.db.model.Soundtrack;
import com.animusic.core.db.table.SoundtrackRepository;
import com.animusic.s3.S3Service;
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

    private final AudioService audioService;

    private final AlbumService albumService;

    private final ImageService imageService;

    private final S3Service s3Service;

    private final JsonMergePatchService jsonMergePatchService;

    public Soundtrack getSoundtrackOrThrow(Integer id) {
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
        soundtrack.setAnime(anime);
        soundtrack.setCreationDate(new Date());
        var blobKey = audioService.createAudioFile(soundtrack, audio);
        soundtrack.setAudioFile(blobKey);
        if (image != null && !image.isEmpty()) {
            var savedImage = imageService.createImageInAnimeDirectory(
                    soundtrack.getAnime().getFolderName(),
                    soundtrack.getAnimeTitle(),
                    image
            );
            soundtrack.setImage(savedImage);
        }
        soundtrack.setAlbum(album);
        log.info("Soundtrack {} created successfully", blobKey);
        return soundtrackRepository.save(soundtrack);
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
        var soundtrack = soundtrackRepository.findById(soundtrackId)
                .orElseThrow(() -> new SoundtrackNotFoundException(soundtrackId));
        var createdAudio = audioService.createAudioFile(soundtrack, audio);
        soundtrack.setAudioFile(createdAudio);
        return soundtrack;
    }

    @Transactional
    public Soundtrack updateImage(MultipartFile image, Integer soundtrackId) {
        var soundtrack = soundtrackRepository.findById(soundtrackId)
                .orElseThrow(() -> new SoundtrackNotFoundException(soundtrackId));
        var createdImage = imageService.createImageInAnimeDirectory(
                soundtrack.getAnime().getFolderName(),
                soundtrack.getAnimeTitle(),
                image
        );
        soundtrack.setImage(createdImage);
        return soundtrack;
    }

    public Soundtrack updateSoundtrack(JsonNode patch, Integer soundtrackId) {
        Soundtrack soundtrack = soundtrackRepository.findById(soundtrackId)
                .orElseThrow(() -> new SoundtrackNotFoundException(soundtrackId));
        Soundtrack res = jsonMergePatchService.mergePatch(patch, soundtrack, Soundtrack.class);
        soundtrackRepository.save(res);
        return res;
    }
}
