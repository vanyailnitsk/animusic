package com.animusic.content.soundtrack;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.animusic.content.playlist.MediaLibraryService;
import com.animusic.core.db.model.Soundtrack;
import com.animusic.core.db.model.User;
import com.animusic.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SoundtrackSavedHelper {

    private static SoundtrackSavedHelper soundtrackSavedHelper;

    private UserService userService;

    private MediaLibraryService mediaLibraryService;

    public static boolean isSaved(Soundtrack soundtrack) {
        Set<Integer> savedTracks = soundtrackSavedHelper.getUserSavedTracksIds();
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
