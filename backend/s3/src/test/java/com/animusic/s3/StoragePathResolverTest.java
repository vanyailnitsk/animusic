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
        assertThat(StoragePathResolver.createSoundtrackFileName("Naruto", "Opening 1", "track.aac"))
                .isEqualTo("Naruto/audio/Opening 1.aac");
        assertThat(StoragePathResolver.createSoundtrackFileName("HxH", "123", "track.mp3"))
                .isEqualTo("HxH/audio/123.mp3");
        assertThatThrownBy(() -> StoragePathResolver.createSoundtrackFileName("Anime", "track", ""))
                .isInstanceOf(NullPointerException.class);
    }
}