package com.ilnitsk.animusic.soundtrack.dto;

import com.ilnitsk.animusic.soundtrack.dao.Soundtrack;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class SoundtrackRequest {
    private String originalTitle;
    private String animeTitle;
    private Integer playlistId;
    public Soundtrack createSoundtrack() {
        return new Soundtrack(originalTitle,animeTitle);
    }
}
