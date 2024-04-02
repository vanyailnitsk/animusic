package com.ilnitsk.animusic.album.dto;

import com.ilnitsk.animusic.album.dao.Album;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CreateAlbumRequest {
    private Integer animeId;
    private String name;
    private String imageUrl;
    public Album getAlbumData() {
        return Album.builder()
                .name(name)
                .imageUrl(imageUrl)
                .build();
    }
}
