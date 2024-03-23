package com.ilnitsk.animusic.user.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserPlaylistDto {
    private String name;
    private String link;
    private List<UserPlaylistSoundtrackDTO> soundtracks;
}
