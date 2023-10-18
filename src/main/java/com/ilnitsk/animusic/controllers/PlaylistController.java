package com.ilnitsk.animusic.controllers;

import com.ilnitsk.animusic.dto.CreatePlaylistRequest;
import com.ilnitsk.animusic.models.Anime;
import com.ilnitsk.animusic.models.Playlist;
import com.ilnitsk.animusic.repositories.AnimeRepository;
import com.ilnitsk.animusic.services.AnimeService;
import com.ilnitsk.animusic.services.PlaylistService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/playlist")
@Slf4j
public class PlaylistController {
    private final PlaylistService playlistService;
    private final AnimeRepository animeRepository;

    @Autowired
    public PlaylistController(PlaylistService playlistService, AnimeService animeService, AnimeRepository animeRepository) {
        this.playlistService = playlistService;

        this.animeRepository = animeRepository;
    }

    @GetMapping("/by-anime/{animeId}")
    public List<Playlist> getPlaylistsByAnime(@PathVariable Integer animeId) {
        log.info("Requested playlists by anime {}", animeId);
        return playlistService.getPlaylistsByAnimeId(animeId);
    }

    @GetMapping("{id}")
    public Playlist getPlaylistById(@PathVariable(required = true) Integer id) {
        log.info("Requested playlist with id {}", id);
        return playlistService.getPlaylistsById(id);
    }

    @PostMapping
    public ResponseEntity<Playlist> createPlaylist(@RequestBody CreatePlaylistRequest request) {
        Optional<Anime> animeOptional = animeRepository.findById(request.getAnimeId());
        if (animeOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Anime anime = animeOptional.get();
        Playlist playlist = request.getPlaylistData();
        playlist.setAnime(anime);
        Playlist savedPlaylist = playlistService.createPlaylist(playlist);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPlaylist);
    }
}
