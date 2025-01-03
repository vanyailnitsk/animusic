package animusic.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import animusic.service.playlist.MediaLibraryService;
import animusic.service.security.UserService;
import animusic.service.soundtrack.SoundtrackSavedHelper;
import animusic.util.JsonMergePatchService;

@Configuration
@Import({
        DatabaseConfig.class,
})
@ComponentScan("animusic.service")
public class ContentConfig {

    @Bean
    public SoundtrackSavedHelper savedHelper(UserService userService, MediaLibraryService mediaLibraryService) {
        var soundtrackSavedHelper = new SoundtrackSavedHelper(userService, mediaLibraryService);
        SoundtrackSavedHelper.setSavedHelper(soundtrackSavedHelper);
        return soundtrackSavedHelper;
    }

    @Bean
    public JsonMergePatchService jsonMergePatchService() {
        return new JsonMergePatchService();
    }

}
