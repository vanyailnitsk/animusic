package com.animusic.api.dto;

import java.util.List;

import lombok.Value;

@Value
public class PlaylistDto {
    Integer id;
    String name;
    PlaylistOwnerDto addedBy;
    List<PlaylistSoundtrackDto> soundtracks;
    CoverArtDto coverArt;
}
