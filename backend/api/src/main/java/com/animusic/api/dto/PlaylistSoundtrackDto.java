package com.animusic.api.dto;

import java.util.Date;

import lombok.Value;

@Value
public class PlaylistSoundtrackDto {
    Date addedAt;
    SoundtrackEntityDto soundtrack;
}
