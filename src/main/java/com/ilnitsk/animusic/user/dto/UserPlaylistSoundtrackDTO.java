package com.ilnitsk.animusic.user.dto;

import com.ilnitsk.animusic.soundtrack.Soundtrack;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserPlaylistSoundtrackDTO {
    private LocalDateTime addedAt;
    private Soundtrack soundtrack;
}
