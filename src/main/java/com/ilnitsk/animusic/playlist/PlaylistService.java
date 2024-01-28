package com.ilnitsk.animusic.playlist;

import com.ilnitsk.animusic.anime.AnimeService;
import com.ilnitsk.animusic.exception.AnimeNotFoundException;
import com.ilnitsk.animusic.exception.BadRequestException;
import com.ilnitsk.animusic.anime.Anime;
import com.ilnitsk.animusic.anime.AnimeRepository;
import com.ilnitsk.animusic.exception.PlaylistNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class PlaylistService {
    private final PlaylistRepository playlistRepository;
    private final AnimeRepository animeRepository;
    private final AnimeService animeService;

    @Autowired
    public PlaylistService(PlaylistRepository playlistRepository, AnimeRepository animeRepository, AnimeService animeService) {
        this.playlistRepository = playlistRepository;
        this.animeRepository = animeRepository;
        this.animeService = animeService;
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

    public ResponseEntity<byte[]> getBanner(Integer playlistId) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new PlaylistNotFoundException(playlistId));
        return animeService.getBanner(playlist.getAnime().getId());
    }

    public void deletePlaylist(Integer id) {
        playlistRepository.deleteById(id);
    }
}

