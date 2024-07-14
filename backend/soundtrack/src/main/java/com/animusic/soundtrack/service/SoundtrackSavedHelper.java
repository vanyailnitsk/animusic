package com.animusic.soundtrack.service;

import com.animusic.core.db.model.Soundtrack;
import com.animusic.core.db.model.User;
import com.animusic.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Aspect
@Component
@RequiredArgsConstructor
public class SoundtrackSavedHelper {

    private static SoundtrackSavedHelper soundtrackSavedHelper;
    private UserService userService;

    public static boolean isSaved(Soundtrack soundtrack) {
        Set<Integer> savedTracks = soundtrackSavedHelper.getUserSavedTracksIds();
        return savedTracks.contains(soundtrack.getId());
    }

    public Set<Integer> getUserSavedTracksIds() {
        Optional<User> user = userService.getUserInSession();
        if (user.isEmpty() || Objects.nonNull(user.get().getFavouriteTracks())) {
            return Set.of();
        }
        return user.get().getFavouriteTracks().getSoundtracks()
                .stream()
                .map(s -> s.getSoundtrack().getId())
                .collect(Collectors.toSet());
    }

//    @AfterReturning(pointcut = "execution(* com.ilnitsk.animusic.album.dto.AlbumConverter.convertToDto(..))", returning = "dto")
//    public void setSavedToAlbum(Object dto) {
//        AlbumDto albumDto = (AlbumDto) dto;
//        Set<Integer> savedTracks = getUserSavedTracksIds();
//        if (Objects.nonNull(albumDto.soundtracks())) {
//            albumDto.soundtracks().stream()
//                    .map(SoundtrackDto::getSoundtrack)
//                    .filter(s -> savedTracks.contains(s.getId()))
//                    .forEach(s -> s.setSaved(true));
//        }
//    }
//
//    @AfterReturning(pointcut = "execution(* com.ilnitsk.animusic.playlist.dto.UserMediaConverter.convertToDto(..))", returning = "dto")
//    public void setSavedToPlaylist(Object dto) {
//        PlaylistDto playlistDto = (PlaylistDto) dto;
//        Set<Integer> savedTracks = getUserSavedTracksIds();
//        if (Objects.nonNull(playlistDto.getSoundtracks())) {
//            playlistDto.getSoundtracks().stream()
//                    .map(PlaylistSoundtrackDto::getSoundtrack)
//                    .filter(s -> savedTracks.contains(s.getId()))
//                    .forEach(s -> s.setSaved(true));
//        }
//    }

}
