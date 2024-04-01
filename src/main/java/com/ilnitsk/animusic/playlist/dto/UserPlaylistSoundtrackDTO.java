package com.ilnitsk.animusic.playlist.dto;

import com.ilnitsk.animusic.soundtrack.dto.SoundtrackEntityDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserPlaylistSoundtrackDTO {
    private LocalDateTime addedAt;
    private SoundtrackEntityDto soundtrack;
}
