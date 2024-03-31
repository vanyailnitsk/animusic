package com.ilnitsk.animusic.album.service;

import com.ilnitsk.animusic.album.dao.Album;
import com.ilnitsk.animusic.album.dto.CreateAlbumRequest;
import com.ilnitsk.animusic.album.dto.UpdateAlbumDto;
import com.ilnitsk.animusic.album.repository.AlbumRepository;
import com.ilnitsk.animusic.anime.Anime;
import com.ilnitsk.animusic.anime.AnimeRepository;
import com.ilnitsk.animusic.anime.AnimeService;
import com.ilnitsk.animusic.exception.AnimeNotFoundException;
import com.ilnitsk.animusic.exception.BadRequestException;
import com.ilnitsk.animusic.exception.PlaylistNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    public Album createPlaylist(CreateAlbumRequest request) {
        Optional<Anime> animeOptional = animeRepository.findById(request.getAnimeId());
        if (animeOptional.isEmpty()) {
            throw new AnimeNotFoundException(request.getAnimeId());
        }
        Anime anime = animeOptional.get();
        if (albumRepository.existsByNameAndAnimeId(request.getName(),request.getAnimeId())) {
            throw new BadRequestException(
                    "Playlist " + request.getName() + " in anime " +anime.getTitle()+" already exists"
            );
        }
        Album album = request.getPlaylistData();
        album.setAnime(anime);
        albumRepository.save(album);
        return album;
    }

    public List<Album> getPlaylistsByAnimeId(Integer animeId) {
        Optional<List<Album>> playlists = albumRepository.getPlaylistsByAnimeId(animeId);
        if (playlists.isEmpty()) {
            throw new AnimeNotFoundException(animeId);
        }
        return playlists.get();
    }

    public Album getPlaylistById(Integer id) {
        Optional<Album> entity = albumRepository.findById(id);
        if (entity.isEmpty()) {
            throw new PlaylistNotFoundException(id);
        }
        Album album = entity.get();
        String animeTitle = album.getAnime().getTitle();
        album.getSoundtracks()
                .forEach(s -> s.setAnimeName(animeTitle));
        String playlistName = album.getName();
        if (playlistName.equals("Openings") || playlistName.equals("Endings")) {
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


    public ResponseEntity<byte[]> getBanner(Integer playlistId) {
        Album album = albumRepository.findById(playlistId)
                .orElseThrow(() -> new PlaylistNotFoundException(playlistId));
        return animeService.getBanner(album.getAnime().getId());
    }

    public void deletePlaylist(Integer id) {
        albumRepository.deleteById(id);
    }

    @Transactional
    public Album updatePlaylist(UpdateAlbumDto playlistDto, Integer playlistId) {
        return albumRepository.findById(playlistId).map(
                playlist -> {
                    playlist.setName(playlistDto.getName());
                    playlist.setImageUrl(playlistDto.getImageUrl());
                    return playlist;
                }
        ).orElseThrow(() -> new PlaylistNotFoundException(playlistId));
    }
}

