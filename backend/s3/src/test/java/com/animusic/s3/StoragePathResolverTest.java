package com.animusic.s3;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StoragePathResolverTest {

    @Test
    void getFileExtension() {
        assertThat(StoragePathResolver.getFileExtension("file.mp3")).isEqualTo(".mp3");
        assertThat(StoragePathResolver.getFileExtension("file.1.2.mp3")).isEqualTo(".mp3");
        assertThat(StoragePathResolver.getFileExtension("file")).isNull();
        assertThat(StoragePathResolver.getFileExtension("")).isNull();
        assertThat(StoragePathResolver.getFileExtension("Naruto/audio/track.aac")).isEqualTo(".aac");
    }

    @Test
    void createSoundtrackFileName() {
        assertThat(StoragePathResolver.soundtrackAudioFile("Naruto", "Opening 1", "track.aac"))
                .isEqualTo("Naruto/audio/Opening 1.aac");
        assertThat(StoragePathResolver.soundtrackAudioFile("HxH", "123", "track.mp3"))
                .isEqualTo("HxH/audio/123.mp3");
        assertThatThrownBy(() -> StoragePathResolver.soundtrackAudioFile("Anime", "track", ""))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void imageInAnimeFolder() {
        assertThat(StoragePathResolver.imageInAnimeFolder("Naruto", "banner", "image.png"))
                .isEqualTo("Naruto/images/banner.png");
        assertThat(StoragePathResolver.imageInAnimeFolder("HxH", "Opening 1", "123.jpeg"))
                .isEqualTo("HxH/images/Opening 1.jpeg");
        assertThatThrownBy(() -> StoragePathResolver.imageInAnimeFolder("Anime", "track", ""))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void imageInUserFolder() {
        assertThat(StoragePathResolver.imageInUserFolder(1, "avatar", "img.jpg"))
                .isEqualTo("users/1/avatar.jpg");
        assertThat(StoragePathResolver.imageInUserFolder(2, "playlist1", "123.png"))
                .isEqualTo("users/2/playlist1.png");
        assertThatThrownBy(() -> StoragePathResolver.imageInUserFolder(2, "p", null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void absoluteUrlTest() {
        StoragePathResolver.setPublicUrl("https://yandex-cloud.net", "animusic");
        var objectUrl = "Naruto/track.mp3";
        assertThat(StoragePathResolver.getAbsoluteFileUrl(objectUrl))
                .isEqualTo("https://yandex-cloud.net/animusic/Naruto/track.mp3");
    }
}