package com.ilnitsk.animusic.soundtrack.dto;

import com.ilnitsk.animusic.album.dto.AlbumItemDto;
import com.ilnitsk.animusic.anime.dto.AnimeItemDto;
import lombok.Data;

@Data
public class SoundtrackEntityDto {
    private Integer id;
    private String originalTitle;
    private String animeTitle;
    private String audioFile;
    private String imageFile;
    private Integer duration;
    private AlbumItemDto album;
    private AnimeItemDto anime;
}
