package com.ilnitsk.animusic.soundtrack.service;

import com.ilnitsk.animusic.album.dto.AlbumDto;
import com.ilnitsk.animusic.playlist.dto.PlaylistDto;
import com.ilnitsk.animusic.playlist.dto.PlaylistSoundtrackDto;
import com.ilnitsk.animusic.soundtrack.dto.SoundtrackDto;
import com.ilnitsk.animusic.soundtrack.dto.SoundtrackEntityDto;
import com.ilnitsk.animusic.user.dao.User;
import com.ilnitsk.animusic.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Aspect
@Component
@RequiredArgsConstructor
public class SoundtrackSavedAspect {

    private final UserService userService;

    @AfterReturning(pointcut = "execution(* com.ilnitsk.animusic.soundtrack.dto.SoundtrackConverter.convertToDto(..))", returning = "dto")
    public void setSavedField(Object dto) {
        SoundtrackDto soundtrackDto = (SoundtrackDto) dto;
        SoundtrackEntityDto entityDto = soundtrackDto.getSoundtrack();
        Set<Integer> savedTracks = getUserSavedTracksIds();
        if (savedTracks.contains(entityDto.getId())) {
            entityDto.setSaved(true);
        }
    }

    public Set<Integer> getUserSavedTracksIds() {
        Optional<User> user = userService.getUserInSession();
        if (user.isEmpty()) {
            return Set.of();
        }
        Set<Integer> savedTracksIds = user.get().getFavouriteTracks().getSoundtracks()
                .stream()
                .map(s -> s.getSoundtrack().getId())
                .collect(Collectors.toSet());
        return savedTracksIds;
    }
    @AfterReturning(pointcut = "execution(* com.ilnitsk.animusic.album.dto.AlbumConverter.convertToDto(..))", returning = "dto")
    public void setSavedToAlbum(Object dto) {
        AlbumDto albumDto = (AlbumDto) dto;
        Set<Integer> savedTracks = getUserSavedTracksIds();
        albumDto.getSoundtracks().stream()
                .map(SoundtrackDto::getSoundtrack)
                .filter(s -> savedTracks.contains(s.getId()))
                .forEach(s -> s.setSaved(true));
    }

    @AfterReturning(pointcut = "execution(* com.ilnitsk.animusic.playlist.dto.UserMediaConverter.convertToDto(..))", returning = "dto")
    public void setSavedToPlaylist(Object dto) {
        PlaylistDto playlistDto = (PlaylistDto) dto;
        Set<Integer> savedTracks = getUserSavedTracksIds();
        playlistDto.getSoundtracks().stream()
                .map(PlaylistSoundtrackDto::getSoundtrack)
                .filter(s -> savedTracks.contains(s.getId()))
                .forEach(s -> s.setSaved(true));
    }

}
