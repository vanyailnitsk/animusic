package com.ilnitsk.animusic.user.service;

import com.ilnitsk.animusic.exception.SoundtrackNotFoundException;
import com.ilnitsk.animusic.soundtrack.Soundtrack;
import com.ilnitsk.animusic.soundtrack.SoundtrackRepository;
import com.ilnitsk.animusic.user.dao.Playlist;
import com.ilnitsk.animusic.user.dao.PlaylistSoundtrack;
import com.ilnitsk.animusic.user.dao.User;
import com.ilnitsk.animusic.user.repository.UserPlaylistRepository;
import com.ilnitsk.animusic.user.repository.UserPlaylistSoundtrackRepository;
import com.ilnitsk.animusic.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserMediaLibraryService {
    private final UserService userService;
    private final UserRepository userRepository;
    private final SoundtrackRepository soundtrackRepository;
    private final UserPlaylistRepository userPlaylistRepository;
    private final UserPlaylistSoundtrackRepository userPlaylistSoundtrackRepository;

    public Playlist getFavouriteTracksPlaylist() {
        User user = userService.getUserInSession();
        return user.getFavouriteTracks();
    }

    @Transactional
    public Playlist createPlaylist(String playlistName) {
        User user = userService.getUserInSession();
        Playlist playlist = Playlist.builder()
                .name(playlistName)
                .user(user)
                .build();
        user.setFavouriteTracks(playlist);
        return userPlaylistRepository.save(playlist);
    }

    @Transactional
    public void addTrackToFavourites(Integer trackId) {
        User user = userService.getUserInSession();
        Playlist playlist;
        if (user.getFavouriteTracks() == null) {
            playlist = createPlaylist("Favourite tracks");
            playlist.setSoundtracks(new ArrayList<>());
        }
        else {
            playlist = user.getFavouriteTracks();
        }
        Soundtrack soundtrack = soundtrackRepository.findById(trackId)
                .orElseThrow(() -> new SoundtrackNotFoundException(trackId));
        if (!userPlaylistSoundtrackRepository.playlistAlreadyContainsSoundtrack(playlist.getId(),soundtrack.getId())) {
            PlaylistSoundtrack playlistSoundtrack = PlaylistSoundtrack.builder()
                    .playlist(playlist)
                    .soundtrack(soundtrack)
                    .addedAt(LocalDateTime.now())
                    .build();
            userPlaylistSoundtrackRepository.save(playlistSoundtrack);
            playlist.getSoundtracks().add(playlistSoundtrack);
        }
    }

    public void deleteTrackFromFavourites(Integer trackId) {
        User user = userService.getUserInSession();
        Playlist favourites = user.getFavouriteTracks();
        userPlaylistSoundtrackRepository.deleteTrackFromPlaylist(favourites.getId(),trackId);
    }
}
