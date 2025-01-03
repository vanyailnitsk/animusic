package animusic.service.soundtrack;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import animusic.core.db.model.Soundtrack;
import animusic.core.db.model.User;
import animusic.service.playlist.MediaLibraryService;
import animusic.service.security.UserService;

@Component
@RequiredArgsConstructor
public class SoundtrackSavedHelper {

    @Setter
    private static SoundtrackSavedHelper savedHelper;

    private final UserService userService;

    private final MediaLibraryService mediaLibraryService;

    public static boolean isSaved(Soundtrack soundtrack) {
        Set<Integer> savedTracks = savedHelper.getUserSavedTracksIds();
        return savedTracks.contains(soundtrack.getId());
    }

    public Set<Integer> getUserSavedTracksIds() {
        Optional<User> user = userService.getUserInSession();
        if (user.isEmpty()) {
            return Set.of();
        }
        var playlist = mediaLibraryService.getFavouritePlaylistOrCreate();
        return playlist.getSoundtracks()
                .stream()
                .map(s -> s.getSoundtrack().getId())
                .collect(Collectors.toSet());
    }

}
