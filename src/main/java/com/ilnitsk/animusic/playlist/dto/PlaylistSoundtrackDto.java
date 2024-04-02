package com.ilnitsk.animusic.playlist.dto;

import com.ilnitsk.animusic.soundtrack.dto.SoundtrackEntityDto;
import lombok.Data;

import java.util.Date;

@Data
public class PlaylistSoundtrackDto {
    private Date addedAt;
    private SoundtrackEntityDto soundtrack;
}
