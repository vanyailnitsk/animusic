package com.animusic.content.conf;

import com.animusic.content.playlist.MediaLibraryService;
import com.animusic.content.soundtrack.SoundtrackSavedHelper;
import com.animusic.core.conf.DatabaseConfig;
import com.animusic.user.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        DatabaseConfig.class,
})
public class ContentConfig {
    @Bean
    public SoundtrackSavedHelper savedHelper(UserService userService, MediaLibraryService mediaLibraryService) {
        var soundtrackSavedHelper = new SoundtrackSavedHelper(userService, mediaLibraryService);
        SoundtrackSavedHelper.setSavedHelper(soundtrackSavedHelper);
        return soundtrackSavedHelper;
    }
}
