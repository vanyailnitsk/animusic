package com.ilnitsk.animusic.controllers;

import com.ilnitsk.animusic.models.Playlist;
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

    @Autowired
    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @GetMapping("/by-anime/{animeId}")
    public List<Playlist> getPlaylistsByAnime(@PathVariable Integer animeId) {
        log.info("Requested playlists by anime {}",animeId);
        return playlistService.getPlaylistsByAnimeId(animeId);
    }

    @GetMapping("{id}")
    public Playlist getPlaylistById(@PathVariable(required = true) Integer id) {
        log.info("Requested playlist with id {}",id);
        return playlistService.getPlaylistsById(id);
    }
}
