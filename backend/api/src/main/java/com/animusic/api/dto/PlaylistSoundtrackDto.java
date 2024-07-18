package com.animusic.api.dto;

import java.util.Date;

import lombok.Data;

@Data
public class PlaylistSoundtrackDto {
    private Date addedAt;
    private SoundtrackEntityDto soundtrack;
}
