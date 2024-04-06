package com.ilnitsk.animusic.playlist.dto;

import com.ilnitsk.animusic.image.dto.CoverArtDto;
import lombok.Data;

import java.util.List;

@Data
public class PlaylistDto {
    private Long id;
    private String name;
    private PlaylistOwnerDto addedBy;
    private List<PlaylistSoundtrackDto> soundtracks;
    private CoverArtDto coverArt;
}
