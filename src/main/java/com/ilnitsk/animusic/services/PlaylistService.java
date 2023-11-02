package com.ilnitsk.animusic.services;

import com.ilnitsk.animusic.dto.CreatePlaylistRequest;
import com.ilnitsk.animusic.exception.AnimeNotFoundException;
import com.ilnitsk.animusic.exception.BadRequestException;
import com.ilnitsk.animusic.exception.PlaylistNotFoundException;
import com.ilnitsk.animusic.models.Anime;
import com.ilnitsk.animusic.models.Playlist;
import com.ilnitsk.animusic.repositories.AnimeRepository;
import com.ilnitsk.animusic.repositories.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class PlaylistService {
    private final PlaylistRepository playlistRepository;
    private final AnimeRepository animeRepository;

    @Autowired
    public PlaylistService(PlaylistRepository playlistRepository, AnimeRepository animeRepository) {
        this.playlistRepository = playlistRepository;
        this.animeRepository = animeRepository;
    }

    public Playlist createPlaylist(CreatePlaylistRequest request) {
        Optional<Anime> animeOptional = animeRepository.findById(request.getAnimeId());
        if (animeOptional.isEmpty()) {
            throw new AnimeNotFoundException(request.getAnimeId());
        }
        Anime anime = animeOptional.get();
        if (playlistRepository.existsByNameAndAnimeId(request.getName(),request.getAnimeId())) {
            throw new BadRequestException(
                    "Playlist " + request.getName() + " in anime " +anime.getTitle()+" already exists"
            );
        }
        Playlist playlist = request.getPlaylistData();
        playlist.setAnime(anime);
        playlistRepository.save(playlist);
        return playlist;
    }

    public List<Playlist> getPlaylistsByAnimeId(Integer animeId) {
        Optional<List<Playlist>> playlists = playlistRepository.getPlaylistsByAnimeId(animeId);
        if (playlists.isEmpty()) {
            throw new AnimeNotFoundException(animeId);
        }
        return playlists.get();
    }

    public Playlist getPlaylistById(Integer id) {
        Optional<Playlist> entity = playlistRepository.findById(id);
        if (entity.isEmpty()) {
            throw new PlaylistNotFoundException(id);
        }
        Playlist playlist = entity.get();
        String animeTitle = playlist.getAnime().getTitle();
        playlist.getSoundtracks()
                .forEach(s -> s.setAnimeName(animeTitle));
        String playlistName = playlist.getName();
        if (playlistName.equals("Openings") || playlistName.equals("Endings")) {
            playlist.getSoundtracks().sort(Comparator.comparingInt(
                    a -> Integer.parseInt(a.getAnimeTitle().split(" ")[1]))
            );
        }
        return entity.get();
    }

    public void deletePlaylist(Integer id) {
        playlistRepository.deleteById(id);
    }
}

