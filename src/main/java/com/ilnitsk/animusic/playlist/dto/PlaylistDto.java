package com.ilnitsk.animusic.playlist.dto;

import lombok.Data;

import java.util.List;

@Data
public class PlaylistDto {
    private Long id;
    private String name;
    private PlaylistOwnerDto addedBy;
    private List<PlaylistSoundtrackDto> soundtracks;
}
