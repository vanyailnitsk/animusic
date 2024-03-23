package com.ilnitsk.animusic.playlist.dto;

import com.ilnitsk.animusic.soundtrack.SoundtrackDto;
import lombok.Data;

import java.util.List;

@Data
public class PlaylistDto {
    private Integer id;
    private String name;
    private String imageUrl;
    private String bannerLink;
    private List<SoundtrackDto> soundtracks;
}
