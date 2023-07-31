package com.ilnitsk.animusic.services;

import com.ilnitsk.animusic.repositories.SoundtrackRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SoundtrackService {
    private final SoundtrackRepository soundtrackRepository;

    @Autowired
    public SoundtrackService(SoundtrackRepository soundtrackRepository) {
        this.soundtrackRepository = soundtrackRepository;
    }


}
