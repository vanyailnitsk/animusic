package com.ilnitsk.animusic.soundtrack;

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
    private String trackType;
    private String videoUrl;

    public Soundtrack createSoundtrack() {
        return new Soundtrack(originalTitle,animeTitle, TrackType.valueOf(trackType));
    }
}
