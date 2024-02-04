package com.ilnitsk.animusic.soundtrack;

import com.ilnitsk.animusic.anime.Anime;
import com.ilnitsk.animusic.anime.AnimeRepository;
import com.ilnitsk.animusic.exception.PlaylistNotFoundException;
import com.ilnitsk.animusic.exception.SoundtrackNotFoundException;
import com.ilnitsk.animusic.file.FileService;
import com.ilnitsk.animusic.playlist.Playlist;
import com.ilnitsk.animusic.playlist.PlaylistRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@Service
@Slf4j
public class SoundtrackService {
    private final SoundtrackRepository soundtrackRepository;
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

    @Transactional(timeout = 10)
    public Soundtrack createSoundtrack(MultipartFile file, Soundtrack soundtrack, Integer playlistId) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new PlaylistNotFoundException(playlistId));
        Anime anime = playlist.getAnime();
        fileService.saveAudio(file, anime.getFolderName(), soundtrack.getAnimeTitle());
        soundtrack.setAnime(anime);
        soundtrack.setAudioFile(
                soundtrack.getAnimeTitle() + fileService.getFileExtension(file.getOriginalFilename())
        );
        Soundtrack savedSoundtrack = soundtrackRepository.save(soundtrack);
        playlist.addSoundtrack(soundtrack);
        log.info("Soundtrack {}/{} created successfully", anime.getFolderName(), soundtrack.getAnimeTitle());
        return savedSoundtrack;
    }

    public void remove(Integer id) {
        Soundtrack soundtrack = soundtrackRepository.findById(id)
                .orElseThrow(() -> new SoundtrackNotFoundException(id));
        String folderName = soundtrack.getAnime().getFolderName();
        fileService.removeFile(folderName, "audio", soundtrack.getAudioFile());
        soundtrackRepository.deleteById(id);
        log.info("Soundtrack {}/{} removed successfully", folderName, soundtrack.getAnimeTitle());
    }
}
