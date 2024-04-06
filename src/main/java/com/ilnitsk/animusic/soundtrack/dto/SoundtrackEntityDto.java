package com.ilnitsk.animusic.soundtrack.dto;

import com.ilnitsk.animusic.album.dto.AlbumItemDto;
import com.ilnitsk.animusic.anime.dto.AnimeItemDto;
import com.ilnitsk.animusic.image.dto.ImageDto;
import lombok.Data;

@Data
public class SoundtrackEntityDto {
    private Integer id;
    private String originalTitle;
    private String animeTitle;
    private String audioFile;
    private ImageDto image;
    private Integer duration;
    private boolean saved;
    private AlbumItemDto album;
    private AnimeItemDto anime;
}
