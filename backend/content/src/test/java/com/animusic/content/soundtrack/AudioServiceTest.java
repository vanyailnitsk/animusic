package com.animusic.content.soundtrack;

import com.animusic.content.IntegrationTestBase;
import com.animusic.core.db.model.Anime;
import com.animusic.core.db.model.Soundtrack;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class AudioServiceTest extends IntegrationTestBase {

    @Autowired
    AudioService audioService;

    @Test
    void createAudioFile() {
        var soundtrack = Soundtrack.builder()
                .animeTitle("Opening 1")
                .anime(Anime.builder()
                        .folderName("Naruto")
                        .build())
                .build();
        var content = "content_of_audiofile";
        var file = new MockMultipartFile("file", "track.aac", "audio/aac", content.getBytes());
        audioService.createAudioFile(soundtrack,file);
        assertThat(s3Service.getObject("Naruto/audio/Opening 1.aac")).isEqualTo(content.getBytes());
    }
}