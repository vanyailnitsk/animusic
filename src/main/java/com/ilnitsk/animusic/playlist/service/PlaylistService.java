package com.ilnitsk.animusic.playlist.service;

import com.ilnitsk.animusic.exception.PlaylistNotFoundException;
import com.ilnitsk.animusic.image.dao.CoverArt;
import com.ilnitsk.animusic.image.dto.CoverArtConverter;
import com.ilnitsk.animusic.image.dto.CreateCoverDto;
import com.ilnitsk.animusic.image.service.CoverArtService;
import com.ilnitsk.animusic.playlist.dao.Playlist;
import com.ilnitsk.animusic.playlist.repository.PlaylistRepository;
import com.ilnitsk.animusic.user.dao.User;
import com.ilnitsk.animusic.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlaylistService {
    private final PlaylistRepository playlistRepository;
    private final UserService userService;
    private final CoverArtConverter coverArtConverter;
    private final CoverArtService coverArtService;

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

    public Playlist getPlaylistById(Integer playlistId) {
        return playlistRepository.findById(playlistId)
                .orElseThrow(() -> new PlaylistNotFoundException(playlistId));
    }

    @Transactional
    public CoverArt createCoverArt(Integer playlistId, MultipartFile imageFile, CreateCoverDto coverArtDto) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new PlaylistNotFoundException(playlistId));
        CoverArt coverArt = coverArtConverter.convertToEntity(coverArtDto);
        CoverArt newCoverArt = coverArtService.createPlaylistCoverArt(
                playlist.getUser().getId(),
                playlist.getName(),
                imageFile,
                coverArt);
        playlist.setCoverArt(newCoverArt);
        return newCoverArt;
    }
}
