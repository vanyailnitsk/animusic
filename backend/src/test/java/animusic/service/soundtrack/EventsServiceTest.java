package animusic.service.soundtrack;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import animusic.core.db.model.Album;
import animusic.core.db.model.Anime;
import animusic.core.db.model.Soundtrack;
import animusic.core.db.model.User;
import animusic.service.IntegrationTestBase;
import animusic.service.soundtrack.EventsService;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class EventsServiceTest extends IntegrationTestBase {

    @Autowired
    EventsService eventsService;

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
        var event = eventsService.addListeningEvent(user, trackId);

        assertThat(trackListeningEventRepository.trackListeningsCount(trackId)).isEqualTo(1);
        assertThat(event.getListenedAt()).isNotNull();
    }
}