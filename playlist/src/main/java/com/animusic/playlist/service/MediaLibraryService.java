package com.animusic.playlist.service;

import com.animusic.playlist.dao.Playlist;
import com.animusic.playlist.dao.PlaylistSoundtrack;
import com.animusic.playlist.repository.PlaylistRepository;
import com.animusic.playlist.repository.PlaylistSoundtrackRepository;
import com.ilnitsk.animusic.exception.SoundtrackNotFoundException;
import com.ilnitsk.animusic.soundtrack.dao.Soundtrack;
import com.ilnitsk.animusic.soundtrack.repository.SoundtrackRepository;
import com.ilnitsk.animusic.user.dao.User;
import com.ilnitsk.animusic.user.repository.UserRepository;
import com.ilnitsk.animusic.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class MediaLibraryService {
    private final UserService userService;
    private final UserRepository userRepository;
    private final SoundtrackRepository soundtrackRepository;
    private final PlaylistRepository playlistRepository;
    private final PlaylistSoundtrackRepository playlistSoundtrackRepository;
    private final PlaylistService playlistService;

    public Playlist getFavouriteTracksPlaylist() {
        User user = userService.getUserInSession().orElseThrow(() -> new RuntimeException("User not found in session"));
        Playlist favouriteTracks =  user.getFavouriteTracks();
        favouriteTracks.getSoundtracks().sort(Comparator.comparing(PlaylistSoundtrack::getAddedAt).reversed());
        return favouriteTracks;
    }

    @Transactional
    public void addTrackToFavourites(Integer trackId) {
        User user = userService.getUserInSession().orElseThrow(() -> new RuntimeException("User not found in session"));
        Playlist playlist;
        if (user.getFavouriteTracks() == null) {
            playlist = playlistService.createPlaylist("Favourite tracks");
            playlist.setSoundtracks(new ArrayList<>());
        }
        else {
            playlist = user.getFavouriteTracks();
        }
        Soundtrack soundtrack = soundtrackRepository.findById(trackId)
                .orElseThrow(() -> new SoundtrackNotFoundException(trackId));
        if (!playlistSoundtrackRepository.playlistAlreadyContainsSoundtrack(playlist.getId(),soundtrack.getId())) {
            PlaylistSoundtrack playlistSoundtrack = PlaylistSoundtrack.builder()
                    .playlist(playlist)
                    .soundtrack(soundtrack)
                    .addedAt(new Date())
                    .build();
            playlistSoundtrackRepository.save(playlistSoundtrack);
            playlist.getSoundtracks().add(playlistSoundtrack);
        }
    }

    public void deleteTrackFromFavourites(Integer trackId) {
        User user = userService.getUserInSession().orElseThrow(() -> new RuntimeException("User not found in session"));
        Playlist favourites = user.getFavouriteTracks();
        playlistSoundtrackRepository.deleteTrackFromPlaylist(favourites.getId(),trackId);
    }
}
