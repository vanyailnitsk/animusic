package com.ilnitsk.animusic.services;

import com.ilnitsk.animusic.models.Anime;
import com.ilnitsk.animusic.models.Soundtrack;
import com.ilnitsk.animusic.repositories.AnimeRepository;
import com.ilnitsk.animusic.repositories.SoundtrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class AudioService {
    private final SoundtrackRepository soundtrackRepository;
    private final AnimeRepository animeRepository;

    @Value("${audiotracks.directory}")
    private String tracksDirectory;

    @Autowired
    public AudioService(SoundtrackRepository soundtrackRepository, AnimeRepository animeRepository) {
        this.soundtrackRepository = soundtrackRepository;
        this.animeRepository = animeRepository;
    }

    public Soundtrack createSoundtrackFromFile(MultipartFile file,Soundtrack soundtrack,String animeTitle) {
        Anime anime = animeRepository.findAnimeByTitle(animeTitle);
        Path path = Paths.get(tracksDirectory,anime.getFolderName());
        int statusCode = Downloader.downloadAudioFromFile(file,path,soundtrack.getAnimeTitle());
        return saveSoundtrack(soundtrack, anime, statusCode);
    }
    public Soundtrack createSoundtrackFromYoutube(String url,Soundtrack soundtrack,String animeTitle) {
        Anime anime = animeRepository.findAnimeByTitle(animeTitle);
        Path path = Paths.get(tracksDirectory,anime.getFolderName());
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
