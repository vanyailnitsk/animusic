package com.ilnitsk.animusic.soundtrack.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ilnitsk.animusic.playlist.dto.PlaylistItemDto;
import lombok.Data;

import java.util.List;

@Data
public class SoundtrackEntityDto {
    private Integer id;
    private String originalTitle;
    private String animeTitle;
    private String audioFile;
    private String imageFile;
    private Integer duration;
    @JsonIgnore
    private List<PlaylistItemDto> playlists;

    public PlaylistItemDto getAlbum() {
        return playlists.get(0);
    }
}
