package com.animusic.content.soundtrack;

import com.animusic.content.IntegrationTestBase;
import com.animusic.core.db.model.Album;
import com.animusic.core.db.model.Anime;
import com.animusic.core.db.model.Soundtrack;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class SoundtrackServiceTest extends IntegrationTestBase {

    @Autowired
    SoundtrackService soundtrackService;

    @Test
    void getSoundtrackNotFound() {
        assertThatThrownBy(() -> soundtrackService.getSoundtrack(1))
                .isInstanceOf(SoundtrackNotFoundException.class);
    }

    @Test
    void getSoundtrack() {
    }

    @Test
    @Transactional
    void createSoundtrack() {
        var anime = Anime.builder()
                .title("Anime-1")
                .folderName("Anime1")
                .build();
        animeRepository.save(anime);
        var album = Album.builder()
                .name("Openings")
                .anime(anime)
                .build();
        albumRepository.save(album);
        var audio = new MockMultipartFile("audio", "track.aac", "audio/aac", "audio".getBytes());
        var image = new MockMultipartFile("image", "logo.png", "img.png", "image".getBytes());
        var soundtrack = Soundtrack.builder()
                .animeTitle("Opening 1")
                .originalTitle("Song")
                .duration(123)
                .build();
        soundtrackService.createSoundtrack(audio, image, soundtrack, album.getId());
        assertThat(soundtrackRepository.findById(soundtrack.getId()).get())
                .isEqualTo(soundtrack);
        var expectedAudioFile = "Anime1/audio/Opening 1.aac";
        assertThat(s3Service.getObject(expectedAudioFile)).isEqualTo("audio".getBytes());
        assertThat(soundtrack.getAudioFile()).isEqualTo(expectedAudioFile);
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