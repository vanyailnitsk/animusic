package com.ilnitsk.animusic.album.service;

import com.ilnitsk.animusic.album.dao.Album;
import com.ilnitsk.animusic.album.dto.CreateAlbumRequest;
import com.ilnitsk.animusic.album.dto.UpdateAlbumDto;
import com.ilnitsk.animusic.album.repository.AlbumRepository;
import com.ilnitsk.animusic.anime.dao.Anime;
import com.ilnitsk.animusic.anime.repository.AnimeRepository;
import com.ilnitsk.animusic.anime.service.AnimeService;
import com.ilnitsk.animusic.exception.AlbumNotFoundException;
import com.ilnitsk.animusic.exception.AnimeNotFoundException;
import com.ilnitsk.animusic.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class AlbumService {
    private final AlbumRepository albumRepository;
    private final AnimeRepository animeRepository;
    private final AnimeService animeService;

    @Autowired
    public AlbumService(AlbumRepository albumRepository, AnimeRepository animeRepository, AnimeService animeService) {
        this.albumRepository = albumRepository;
        this.animeRepository = animeRepository;
        this.animeService = animeService;
    }

    public Album createAlbum(CreateAlbumRequest request) {
        Optional<Anime> animeOptional = animeRepository.findById(request.getAnimeId());
        if (animeOptional.isEmpty()) {
            throw new AnimeNotFoundException(request.getAnimeId());
        }
        Anime anime = animeOptional.get();
        if (albumRepository.existsByNameAndAnimeId(request.getName(),request.getAnimeId())) {
            throw new BadRequestException(
                    "Album " + request.getName() + " in anime " +anime.getTitle()+" already exists"
            );
        }
        Album album = request.getAlbumData();
        album.setAnime(anime);
        albumRepository.save(album);
        return album;
    }

    public List<Album> getAlbumsByAnimeId(Integer animeId) {
        Optional<List<Album>> albums = albumRepository.getAlbumsByAnimeId(animeId);
        if (albums.isEmpty()) {
            throw new AnimeNotFoundException(animeId);
        }
        return albums.get();
    }

    public Album getAlbumById(Integer id) {
        Optional<Album> entity = albumRepository.findById(id);
        if (entity.isEmpty()) {
            throw new AlbumNotFoundException(id);
        }
        Album album = entity.get();
        String albumName = album.getName();
        if (albumName.equals("Openings") || albumName.equals("Endings")) {
            album.getSoundtracks().sort(Comparator.comparingInt(
                    a -> {
                        String title = a.getAnimeTitle();
                        if (title.matches(".*\\d+-\\d+.*")) {
                            // Формат "Ending 5-6", извлекаем оба номера и возвращаем их среднее
                            String[] parts = title.split("-");
                            int number1 = Integer.parseInt(parts[0].replaceAll("\\D+", ""));
                            int number2 = Integer.parseInt(parts[1].replaceAll("\\D+", ""));
                            return (number1 + number2) / 2;
                        } else {
                            // Стандартный формат, извлекаем номер
                            return Integer.parseInt(title.replaceAll("\\D+", ""));
                        }
                    }
            ));
        }
        return entity.get();
    }

    public void deleteAlbum(Integer id) {
        albumRepository.deleteById(id);
    }

    @Transactional
    public Album updateAlbum(UpdateAlbumDto albumDto, Integer albumId) {
        return albumRepository.findById(albumId).map(
                album -> {
                    album.setName(albumDto.getName());
                    return album;
                }
        ).orElseThrow(() -> new AlbumNotFoundException(albumId));
    }
}

