package com.ilnitsk.animusic.controllers;

import com.ilnitsk.animusic.services.SoundtrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/soundtracks")
public class SountrackController {
    private final SoundtrackService soundtrackService;

    @Autowired
    public SountrackController(SoundtrackService soundtrackService) {
        this.soundtrackService = soundtrackService;
    }
}
