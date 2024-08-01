package com.animusic.content.playlist;

import java.util.Comparator;
import java.util.Date;
import java.util.Optional;

import com.animusic.content.soundtrack.SoundtrackNotFoundException;
import com.animusic.core.db.model.Playlist;
import com.animusic.core.db.model.PlaylistSoundtrack;
import com.animusic.core.db.model.Soundtrack;
import com.animusic.core.db.model.User;
import com.animusic.core.db.table.PlaylistRepository;
import com.animusic.core.db.table.PlaylistSoundtrackRepository;
import com.animusic.core.db.table.SoundtrackRepository;
import com.animusic.user.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.animusic.core.db.table.PlaylistRepository.FAVOURITE_PLAYLIST_NAME;

@Service
@RequiredArgsConstructor
public class MediaLibraryService {
    private final UserService userService;
    private final SoundtrackRepository soundtrackRepository;
    private final PlaylistRepository playlistRepository;
    private final PlaylistSoundtrackRepository playlistSoundtrackRepository;
    private final PlaylistService playlistService;

    public Optional<Playlist> getFavouritePlaylist(@NonNull User user) {
        var favouriteTracks = playlistRepository.getUserFavouritePlaylist(user.getId());
        if (favouriteTracks.isEmpty()) {
            return favouriteTracks;
        }
        favouriteTracks.get().getSoundtracks().sort(Comparator.comparing(PlaylistSoundtrack::getAddedAt).reversed());
        return favouriteTracks;
    }

    @Transactional
    public Playlist getFavouritePlaylistOrCreate() {
        var user = userService.getUserInSession()
                .orElseThrow(() -> new RuntimeException("User not found in session"));
        return getFavouritePlaylist(user)
                .orElse(playlistService.createPlaylist(FAVOURITE_PLAYLIST_NAME));
    }

    @Transactional
    public void addTrackToFavourites(Integer trackId) {
        Playlist playlist = getFavouritePlaylistOrCreate();
        Soundtrack soundtrack = soundtrackRepository.findById(trackId)
                .orElseThrow(() -> new SoundtrackNotFoundException(trackId));
        boolean alreadyContainsTrack = playlist.getSoundtracks().stream()
                .map(s -> s.getSoundtrack().getId())
                .anyMatch(s -> s.equals(trackId));
        if (!alreadyContainsTrack) {
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
        var favourites = getFavouritePlaylist(user);
        favourites.ifPresent(
                playlist -> playlistSoundtrackRepository.deleteTrackFromPlaylist(playlist.getId(), trackId)
        );
    }
}
