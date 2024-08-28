package com.animusic.content.soundtrack;

import com.animusic.content.IntegrationTestBase;
import com.animusic.core.db.model.Album;
import com.animusic.core.db.model.Anime;
import com.animusic.core.db.model.Soundtrack;
import com.animusic.core.db.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class ListeningStatsServiceTest extends IntegrationTestBase {

    @Autowired
    ListeningStatsService listeningStatsService;

    @Test
    void addListeningEvent() {
        var anime = Anime.builder()
                .folderName("Naruto")
                .build();
        animeRepository.save(anime);
        var album = Album.builder()
                .name("Openings")
                .anime(anime)
                .build();
        albumRepository.save(album);
        var soundtrack = Soundtrack.builder()
                .animeTitle("Opening 1")
                .anime(anime)
                .album(album)
                .build();

        soundtrackRepository.save(soundtrack);

        var user = User.builder().username("user").build();
        userRepository.save(user);
        var trackId = soundtrack.getId();
        var event = listeningStatsService.addListeningEvent(user, trackId);

        assertThat(trackListeningEventRepository.trackListeningsCount(trackId)).isEqualTo(1);
        assertThat(event.getListenedAt()).isNotNull();
    }
}