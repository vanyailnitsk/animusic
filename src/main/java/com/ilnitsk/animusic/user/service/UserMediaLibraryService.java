package com.ilnitsk.animusic.user.service;

import com.ilnitsk.animusic.exception.SoundtrackNotFoundException;
import com.ilnitsk.animusic.soundtrack.Soundtrack;
import com.ilnitsk.animusic.soundtrack.SoundtrackRepository;
import com.ilnitsk.animusic.user.UserService;
import com.ilnitsk.animusic.user.dao.User;
import com.ilnitsk.animusic.user.dao.UserPlaylist;
import com.ilnitsk.animusic.user.dao.UserPlaylistSoundtrack;
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

    public UserPlaylist getFavouriteTracksPlaylist() {
        User user = userService.getUserInSession();
        return user.getFavouriteTracks();
    }

    @Transactional
    public UserPlaylist createPlaylist(String playlistName) {
        User user = userService.getUserInSession();
        UserPlaylist userPlaylist = UserPlaylist.builder()
                .name(playlistName)
                .user(user)
                .build();
        user.setFavouriteTracks(userPlaylist);
        return userPlaylistRepository.save(userPlaylist);
    }

    @Transactional
    public void addTrackToFavourites(Integer trackId) {
        User user = userService.getUserInSession();
        UserPlaylist userPlaylist;
        if (user.getFavouriteTracks() == null) {
            userPlaylist = createPlaylist("Favourite tracks");
            userPlaylist.setSoundtracks(new ArrayList<>());
        }
        else {
            userPlaylist = user.getFavouriteTracks();
        }
        Soundtrack soundtrack = soundtrackRepository.findById(trackId)
                .orElseThrow(() -> new SoundtrackNotFoundException(trackId));
        if (!userPlaylistSoundtrackRepository.playlistAlreadyContainsSoundtrack(userPlaylist.getId(),soundtrack.getId())) {
            UserPlaylistSoundtrack playlistSoundtrack = UserPlaylistSoundtrack.builder()
                    .playlist(userPlaylist)
                    .soundtrack(soundtrack)
                    .addedAt(LocalDateTime.now())
                    .build();
            userPlaylistSoundtrackRepository.save(playlistSoundtrack);
            userPlaylist.getSoundtracks().add(playlistSoundtrack);
        }
    }
}
