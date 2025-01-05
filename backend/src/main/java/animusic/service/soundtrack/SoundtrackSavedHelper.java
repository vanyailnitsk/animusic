package animusic.service.soundtrack;

import java.util.Set;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;
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

    public static boolean isSaved(Soundtrack soundtrack, User currentUser) {
        Set<Integer> savedTracks = savedHelper.getUserSavedTracksIds(currentUser);
        return savedTracks.contains(soundtrack.getId());
    }

    public Set<Integer> getUserSavedTracksIds(@Nullable User user) {
        if (user == null) {
            return Set.of();
        }
        var playlist = mediaLibraryService.getFavouritePlaylistOrCreate(user);
        return playlist.getSoundtracks()
                .stream()
                .map(s -> s.getSoundtrack().getId())
                .collect(Collectors.toSet());
    }

}
