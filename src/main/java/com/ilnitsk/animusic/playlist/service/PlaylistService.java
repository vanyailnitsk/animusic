package com.ilnitsk.animusic.playlist.service;

import com.ilnitsk.animusic.playlist.dao.Playlist;
import com.ilnitsk.animusic.playlist.repository.PlaylistRepository;
import com.ilnitsk.animusic.user.dao.User;
import com.ilnitsk.animusic.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlaylistService {
    private final PlaylistRepository playlistRepository;
    private final UserService userService;

    @Transactional
    public Playlist createPlaylist(String playlistName) {
        User user = userService.getUserInSession().orElseThrow(() -> new RuntimeException("User not found in session"));
        Playlist playlist = Playlist.builder()
                .name(playlistName)
                .user(user)
                .build();
        user.setFavouriteTracks(playlist);
        return playlistRepository.save(playlist);
    }
}
