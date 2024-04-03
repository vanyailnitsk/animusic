package com.ilnitsk.animusic.album.dto;

import com.ilnitsk.animusic.anime.dto.AnimeItemDto;
import com.ilnitsk.animusic.image.CoverArtDto;
import com.ilnitsk.animusic.soundtrack.dto.SoundtrackDto;
import lombok.Data;

import java.util.List;

@Data
public class AlbumDto {
    private Integer id;
    private String name;
    private AnimeItemDto anime;
    private String link;
    private List<SoundtrackDto> soundtracks;
    private CoverArtDto coverArt;
}
