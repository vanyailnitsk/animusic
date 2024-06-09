package com.animusic.album.dto;

import com.animusic.album.dao.Album;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CreateAlbumDto {
    private Integer animeId;
    private String name;
    public Album getAlbumData() {
        return Album.builder()
                .name(name)
                .build();
    }
}
