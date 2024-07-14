package com.animusic.playlist.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

import com.animusic.core.db.model.Playlist;
import com.animusic.core.db.model.PlaylistSoundtrack;
import com.animusic.core.db.model.Soundtrack;
import com.animusic.core.db.model.User;
import com.animusic.core.db.table.PlaylistRepository;
import com.animusic.core.db.table.PlaylistSoundtrackRepository;
import com.animusic.core.db.table.SoundtrackRepository;
import com.animusic.core.db.table.UserRepository;
import com.animusic.soundtrack.SoundtrackNotFoundException;
import com.animusic.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
