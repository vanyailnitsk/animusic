package com.ilnitsk.animusic.playlist.dto;

import com.ilnitsk.animusic.playlist.Playlist;
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
    public Playlist getPlaylistData() {
        return Playlist.builder()
                .name(name)
                .imageUrl(imageUrl)
                .build();
    }
}
