package com.ilnitsk.animusic.playlist;

import com.ilnitsk.animusic.anime.AnimeRepository;
import com.ilnitsk.animusic.playlist.dto.CreatePlaylistRequest;
import com.ilnitsk.animusic.playlist.dto.PlaylistConverter;
import com.ilnitsk.animusic.playlist.dto.PlaylistDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/playlist")
@Slf4j
@RequiredArgsConstructor
public class PlaylistController {
    private final PlaylistService playlistService;
    private final AnimeRepository animeRepository;
    private final PlaylistConverter playlistConverter;
    @GetMapping("/by-anime/{animeId}")
    public List<PlaylistDto> getPlaylistsByAnime(@PathVariable Integer animeId) {
        log.info("Requested playlists by anime {}", animeId);
        List<Playlist> playlists = playlistService.getPlaylistsByAnimeId(animeId);
        List<PlaylistDto> playlistDtos = playlistConverter.convertListToDto(playlists);
        playlistDtos.forEach(s -> s.setLink("/api/playlist/"+s.getId()));
        return playlistDtos;
    }

    @GetMapping("{id}")
    public PlaylistDto getPlaylistById(@PathVariable Integer id, HttpServletRequest request) {
        log.info("Requested playlist with id {}", id);
        Playlist playlist = playlistService.getPlaylistById(id);
        PlaylistDto playlistDto = playlistConverter.convertToDto(playlist);
        playlistDto.setLink(request.getRequestURI());
        return playlistDto;
    }

    @GetMapping("/images/banner/{id}")
    public ResponseEntity<byte[]> getBanner(@PathVariable("id") Integer playlistId) {
        return playlistService.getBanner(playlistId);
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
        log.info("Playlist with id {} deleted",id);
    }
}
