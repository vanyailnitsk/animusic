package com.animusic.api.dto;

import lombok.Data;

import java.util.Date;

@Data
public class PlaylistSoundtrackDto {
    private Date addedAt;
    private SoundtrackEntityDto soundtrack;
}
