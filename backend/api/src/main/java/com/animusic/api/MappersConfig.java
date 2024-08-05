package com.animusic.api;

import com.animusic.content.playlist.MediaLibraryService;
import com.animusic.content.soundtrack.SoundtrackSavedHelper;
import com.animusic.user.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MappersConfig {

    @Bean
    public SoundtrackSavedHelper savedHelper(UserService userService, MediaLibraryService mediaLibraryService) {
        var soundtrackSavedHelper = new SoundtrackSavedHelper(userService, mediaLibraryService);
        SoundtrackSavedHelper.setSavedHelper(soundtrackSavedHelper);
        return soundtrackSavedHelper;
    }
}
