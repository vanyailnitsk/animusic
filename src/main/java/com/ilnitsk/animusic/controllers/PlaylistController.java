package com.ilnitsk.animusic.controllers;

import com.ilnitsk.animusic.dto.CreatePlaylistRequest;
import com.ilnitsk.animusic.models.Playlist;
import com.ilnitsk.animusic.repositories.AnimeRepository;
import com.ilnitsk.animusic.services.AnimeService;
import com.ilnitsk.animusic.services.PlaylistService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/playlist")
@Slf4j
public class PlaylistController {
    private final PlaylistService playlistService;
    private final AnimeRepository animeRepository;

    @Autowired
    public PlaylistController(PlaylistService playlistService, AnimeRepository animeRepository) {
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
        return playlistService.getPlaylistById(id);
    }

    @PostMapping
    public Playlist createPlaylist(@RequestBody CreatePlaylistRequest request) {
        Playlist playlist = playlistService.createPlaylist(request);
        log.info("Playlist {} in anime {} created",request.getName(),request.getAnimeId());
        return playlist;
    }
    @DeleteMapping("{id}")
    public void deletePlaylist(@PathVariable Integer id) {
        playlistService.deletePlaylist(id);
    }
}
