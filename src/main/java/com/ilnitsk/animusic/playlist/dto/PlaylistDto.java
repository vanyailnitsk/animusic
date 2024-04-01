package com.ilnitsk.animusic.playlist.dto;

import lombok.Data;

import java.util.List;

@Data
public class PlaylistDto {
    private String name;
    private String link;
    private List<PlaylistSoundtrackDto> soundtracks;
}
