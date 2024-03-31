package com.ilnitsk.animusic.playlist.dto;

import com.ilnitsk.animusic.playlist.Album;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CreatePlaylistRequest {
    private Integer animeId;
    private String name;
    private String imageUrl;
    public Album getPlaylistData() {
        return Album.builder()
                .name(name)
                .imageUrl(imageUrl)
                .build();
    }
}
