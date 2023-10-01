package com.ilnitsk.animusic.controllers;

import com.ilnitsk.animusic.services.SoundtrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/soundtracks")
public class SountrackController {
    private final SoundtrackService soundtrackService;

    @Autowired
    public SountrackController(SoundtrackService soundtrackService) {
        this.soundtrackService = soundtrackService;
    }

    @GetMapping("/play/{trackId}")
    public ResponseEntity<Resource> playTrack(
            @PathVariable Integer trackId,
            @RequestHeader(value = HttpHeaders.RANGE,required = false) String range) throws IOException {
        return soundtrackService.getAudioStream(trackId, range);
    }


}
