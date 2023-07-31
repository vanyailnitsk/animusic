package com.ilnitsk.animusic.controllers;

import com.ilnitsk.animusic.models.Soundtrack;
import com.ilnitsk.animusic.models.TrackType;
import com.ilnitsk.animusic.services.AudioService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/audio")
public class AudioManagementController {
    private final AudioService audioService;

    @Autowired
    public AudioManagementController(AudioService audioService) {
        this.audioService = audioService;
    }

    @PostMapping("/upload-from-file")
    public Soundtrack uploadAudioFromFile(@RequestPart("file") MultipartFile file,
                                       @ModelAttribute SoundtrackRequest request) {
        return audioService.createSoundtrackFromFile(
                file,request.createSoundtrack(),request.getAnime() );
    }

    @PostMapping("upload-from-youtube")
    public Soundtrack uploadAudioFromYoutube(@RequestBody SoundtrackRequest request) {
        return audioService.createSoundtrackFromYoutube(
                request.getVideoUrl(),request.createSoundtrack(),request.getAnime());
    }
}

@AllArgsConstructor
@Getter
@Setter
class SoundtrackRequest {
    private String originalTitle;
    private String animeTitle;
    private String anime;
    private String trackType;
    private String videoUrl;

    public Soundtrack createSoundtrack() {
        return new Soundtrack(originalTitle,animeTitle,TrackType.valueOf(trackType));
    }
}
