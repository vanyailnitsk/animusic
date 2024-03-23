package com.ilnitsk.animusic.user.controller;

import com.ilnitsk.animusic.user.dao.UserPlaylist;
import com.ilnitsk.animusic.user.dto.UserMediaConverter;
import com.ilnitsk.animusic.user.dto.UserPlaylistDto;
import com.ilnitsk.animusic.user.service.UserMediaLibraryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-media-library")
@RequiredArgsConstructor
public class UserMediaLibraryController {
    private final UserMediaLibraryService userMediaLibraryService;
    private final UserMediaConverter userMediaConverter;

    @GetMapping("/favourites")
    public UserPlaylistDto getFavouriteTracksPlaylist(HttpServletRequest request) {
        UserPlaylist playlist = userMediaLibraryService.getFavouriteTracksPlaylist();
        UserPlaylistDto dto = userMediaConverter.convertToDto(playlist);
        dto.setLink(request.getRequestURI());
        return dto;
    }

    @PostMapping("/favourites")
    public void addTrackToFavourites(@RequestParam Integer trackId) {
        userMediaLibraryService.addTrackToFavourites(trackId);
    }

    @DeleteMapping("/favourites")
    public void deleteTrackFromFavourites(@RequestParam Integer trackId) {
        userMediaLibraryService.deleteTrackFromFavourites(trackId);
    }

    @PostMapping
    public UserPlaylist createPlaylist(@RequestParam String playlistName) {
        return userMediaLibraryService.createPlaylist(playlistName);
    }


}
