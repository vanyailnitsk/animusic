package animusic.service.playlist;

import java.util.Comparator;
import java.util.Date;
import java.util.Optional;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import animusic.core.db.model.Playlist;
import animusic.core.db.model.PlaylistSoundtrack;
import animusic.core.db.model.User;
import animusic.core.db.table.PlaylistRepository;
import animusic.core.db.table.PlaylistSoundtrackRepository;
import animusic.core.db.table.SoundtrackRepository;
import animusic.service.security.UserService;
import animusic.service.soundtrack.SoundtrackNotFoundException;

import static animusic.core.db.table.PlaylistRepository.FAVOURITE_PLAYLIST_NAME;

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
    public Playlist getFavouritePlaylistOrCreate(User currentUser) {
        return getFavouritePlaylist(currentUser)
                .orElseGet(() -> playlistService.createPlaylistForUser(FAVOURITE_PLAYLIST_NAME, currentUser));
    }

    @Transactional
    public void addTrackToFavourites(Integer trackId, User currentUser) {
        var playlist = getFavouritePlaylistOrCreate(currentUser);
        var soundtrack = soundtrackRepository.findById(trackId)
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

    public void deleteTrackFromFavourites(Integer trackId, User currentUser) {
        var favourites = getFavouritePlaylist(currentUser);
        favourites.ifPresent(
                playlist -> playlistSoundtrackRepository.deleteTrackFromPlaylist(playlist.getId(), trackId)
        );
    }
}
