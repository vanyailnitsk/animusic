package com.ilnitsk.animusic.soundtrack.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.ilnitsk.animusic.album.dao.Album;
import com.ilnitsk.animusic.album.repository.AlbumRepository;
import com.ilnitsk.animusic.anime.dao.Anime;
import com.ilnitsk.animusic.exception.AlbumNotFoundException;
import com.ilnitsk.animusic.exception.BadRequestException;
import com.ilnitsk.animusic.exception.SoundtrackNotFoundException;
import com.ilnitsk.animusic.file.FileService;
import com.ilnitsk.animusic.image.service.ImageService;
import com.ilnitsk.animusic.s3.S3Service;
import com.ilnitsk.animusic.soundtrack.dao.Soundtrack;
import com.ilnitsk.animusic.soundtrack.dto.UpdateSoundtrackDto;
import com.ilnitsk.animusic.soundtrack.repository.SoundtrackRepository;
import com.ilnitsk.animusic.util.JsonMergePatchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@Service
@Slf4j
@RequiredArgsConstructor
public class SoundtrackService {
    private final SoundtrackRepository soundtrackRepository;
    private final AlbumRepository albumRepository;
    private final FileService fileService;
    private final ImageService imageService;
    private final S3Service s3Service;
    private final JsonMergePatchService jsonMergePatchService;

    public Soundtrack getSoundtrack(Integer id) {
        return soundtrackRepository.findById(id)
                .orElseThrow(() -> new SoundtrackNotFoundException(id));
    }

    public ResponseEntity<StreamingResponseBody> getAudioStream(Integer trackId, HttpRange range) {
        Soundtrack soundtrack = soundtrackRepository.findById(trackId)
                .orElseThrow(() -> new SoundtrackNotFoundException(trackId));
        String animeFolder = soundtrack.getAnime().getFolderName();
        String trackName = soundtrack.getAudioFile();
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

    public void updateTrackDuration(Soundtrack soundtrack) {
        soundtrack.setDuration(fileService.getTrackDuration(soundtrack.getAnime().getFolderName(),soundtrack.getAudioFile()));
        soundtrackRepository.save(soundtrack);
    }

    public void updateAllTracksDuration() {
        soundtrackRepository.findAll()
                .forEach(this::updateTrackDuration);
    }
    @Transactional(timeout = 30)
    public Soundtrack createSoundtrack(MultipartFile audio, MultipartFile image,Soundtrack soundtrack, Integer playlistId) {
        Album album = albumRepository.findById(playlistId)
                .orElseThrow(() -> new AlbumNotFoundException(playlistId));
        Anime anime = album.getAnime();
        String fileName = "%s/audio/%s".formatted(anime.getFolderName(),soundtrack.getAnimeTitle());
        String blobKey = s3Service.createAudio(fileName,audio);
        soundtrack.setAnime(anime);
        soundtrack.setAudioFile(blobKey);
        if (!image.isEmpty()) {
            imageService.createSoundtrackImage(soundtrack,image);
        }
        Soundtrack savedSoundtrack = soundtrackRepository.save(soundtrack);
        album.addSoundtrack(soundtrack);
        log.info("Soundtrack {} created successfully",blobKey);
        return savedSoundtrack;
    }



    @Transactional
    public Soundtrack setImage(Integer soundtrackId, MultipartFile image) {
        Soundtrack soundtrack = soundtrackRepository.findById(soundtrackId)
                .orElseThrow(() -> new SoundtrackNotFoundException(soundtrackId));
        imageService.createSoundtrackImage(soundtrack,image);
        return soundtrack;
    }

    public void remove(Integer id) {
        Soundtrack soundtrack = soundtrackRepository.findById(id)
                .orElseThrow(() -> new SoundtrackNotFoundException(id));
        String folderName = soundtrack.getAnime().getFolderName();
        s3Service.deleteObject(soundtrack.getAudioFile());
        imageService.deleteImage(soundtrack.getImage());
        soundtrackRepository.deleteById(id);
        log.info("Soundtrack {}/{} removed successfully", folderName, soundtrack.getAnimeTitle());
    }

    @Transactional
    public Soundtrack updateSoundtrack(UpdateSoundtrackDto updateSoundtrackDto, Integer soundtrackId) {
        return soundtrackRepository.findById(soundtrackId).map(
                soundtrack -> {
                    soundtrack.setOriginalTitle(updateSoundtrackDto.getOriginalTitle());
                    soundtrack.setAnimeTitle(updateSoundtrackDto.getAnimeTitle());
                    soundtrack.setDuration(updateSoundtrackDto.getDuration());
                    return soundtrack;
                }
        ).orElseThrow(() -> new SoundtrackNotFoundException(soundtrackId));
    }

    @Transactional
    public Soundtrack updateAudio(MultipartFile audio, Integer soundtrackId) {
        Soundtrack soundtrack = soundtrackRepository.findById(soundtrackId)
                .orElseThrow(() -> new SoundtrackNotFoundException(soundtrackId));
        if (audio.isEmpty()) {
            throw new BadRequestException("Содержимое аудиофайла не может быть пустым!");
        }
        String fileName = "%s/audio/%s".formatted(soundtrack.getAnime().getFolderName(),soundtrack.getAnimeTitle());
        String blobKey = s3Service.createAudio(fileName,audio);
        soundtrack.setAudioFile(blobKey);
        return soundtrack;
    }

    public Soundtrack updateSoundtrack(JsonNode patch, Integer soundtrackId) {
        Soundtrack soundtrack = soundtrackRepository.findById(soundtrackId)
                .orElseThrow(() -> new SoundtrackNotFoundException(soundtrackId));
        Soundtrack soundtrackPatched = jsonMergePatchService.mergePatch(patch,soundtrack,Soundtrack.class);
        return soundtrackRepository.save(soundtrackPatched);
    }
}
