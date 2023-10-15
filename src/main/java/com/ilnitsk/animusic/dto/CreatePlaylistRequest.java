package com.ilnitsk.animusic.dto;

import com.ilnitsk.animusic.models.Playlist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
