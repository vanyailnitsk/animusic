package com.ilnitsk.animusic.anime.dto;

import com.ilnitsk.animusic.album.dto.AlbumItemDto;
import lombok.Data;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Data
public class AnimeDto {
    private Integer id;
    private String title;
    private String studio;
    private Year releaseYear;
    private String description;
    private String folderName;
    private String bannerImagePath;
    private String cardImagePath;
    private List<AlbumItemDto> albums = new ArrayList<>();
}
