package com.animusic.api.dto;

import java.util.List;

import lombok.Data;

@Data
public class PlaylistDto {
    private Long id;
    private String name;
    private PlaylistOwnerDto addedBy;
    private List<PlaylistSoundtrackDto> soundtracks;
    private CoverArtDto coverArt;
}
