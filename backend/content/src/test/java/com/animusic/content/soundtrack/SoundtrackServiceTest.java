package com.animusic.content.soundtrack;

import com.animusic.content.IntegrationTestBase;
import com.animusic.core.db.model.Soundtrack;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class SoundtrackServiceTest extends IntegrationTestBase {

    @Autowired
    SoundtrackService soundtrackService;

    @Test
    void getSoundtrackNotFound() {
        assertThatThrownBy(() -> soundtrackService.getSoundtrack(1123))
                .isInstanceOf(SoundtrackNotFoundException.class);
    }

    @Test
    void getSoundtrack() {
        assertThat(soundtrackService.getSoundtrack(1)).isNotNull();
    }

    @Test
    void createSoundtrack() {
        var audio = new MockMultipartFile("audio", "track.aac", "audio/aac", "audio".getBytes());
        var image = new MockMultipartFile("image", "logo.png", "img/png", "image".getBytes());
        var soundtrack = Soundtrack.builder()
                .animeTitle("Opening 1")
                .originalTitle("Song")
                .duration(123)
                .build();
        soundtrackService.createSoundtrack(audio, image, soundtrack, 1);
        assertThat(soundtrackRepository.findById(soundtrack.getId()).get())
                .isEqualTo(soundtrack);
        var expectedAudioFile = "Anime1/audio/Opening 1.aac";
        assertThat(s3Service.getObject(expectedAudioFile)).isEqualTo("audio".getBytes());
        assertThat(soundtrack.getAudioFile()).isEqualTo(expectedAudioFile);

        var expectedImage = "Anime1/images/Opening 1.png";
        assertThat(s3Service.getObject(expectedImage)).isEqualTo("image".getBytes());
        assertThat(soundtrack.getImage().getSource()).isEqualTo(expectedImage);
    }

    @Test
    void setImage() {
    }

    @Test
    void remove() {
    }

    @Test
    void updateSoundtrack() {
    }

    @Test
    void updateAudio() {
    }

    @Test
    void testUpdateSoundtrack() {
    }
}