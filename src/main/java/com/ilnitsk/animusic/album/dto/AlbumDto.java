package com.ilnitsk.animusic.album.dto;

import com.ilnitsk.animusic.soundtrack.dto.SoundtrackDto;
import lombok.Data;

import java.util.List;

@Data
public class AlbumDto {
    private Integer id;
    private String name;
    private String imageUrl;
    private String bannerLink;
    private String link;
    private List<SoundtrackDto> soundtracks;
}
