package com.ilnitsk.animusic.repositories;


import com.ilnitsk.animusic.dto.CreatePlaylistRequest;
import com.ilnitsk.animusic.models.Anime;
import com.ilnitsk.animusic.models.Playlist;
import com.ilnitsk.animusic.models.Soundtrack;
import com.ilnitsk.animusic.services.PlaylistService;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Year;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PlaylistRepositoryIntegrationTest {


    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private SoundtrackRepository soundtrackRepository;

    @Autowired
    private AnimeRepository animeRepository;


    @Test
    public void testCreatePlaylistWithRelatedEntities() {
        Anime anime = new Anime("Naruto", "mock", Year.of(2002), "", "");
        animeRepository.save(anime);
        CreatePlaylistRequest request = new CreatePlaylistRequest(
                anime.getId(),
                "My Playlist",
                "/"
        );
        Playlist playlist = request.getPlaylistData();
        playlist.setAnime(anime);
        Playlist createdPlaylist = playlistRepository.save(playlist);
        assertThat(createdPlaylist).isNotNull();
        assertThat(createdPlaylist.getAnime()).isNotNull();
        assertThat(playlistRepository.getPlaylistsByAnimeId(1)).isNotEmpty();
        assertThat(createdPlaylist).isEqualTo(playlist);
        assertThat(createdPlaylist.getSoundtracks()).isNull();
    }

    @Test
    public void testDeletePlaylistWithRelatedEntities() {
        Anime anime = new Anime("Naruto", "mock", Year.of(2002), "", "");
        animeRepository.save(anime);
        Playlist playlist = new Playlist();
        playlist.setAnime(anime);
        playlist.setName("My Playlist");
        Soundtrack soundtrack1 = Soundtrack.builder()
                .animeTitle("Song 1")
                .anime(anime)
                .build();
        soundtrack1.setAnime(anime);
        soundtrackRepository.save(soundtrack1);
        Soundtrack soundtrack2 = Soundtrack.builder()
                .animeTitle("Song 2")
                .anime(anime)
                .build();
        soundtrack2.setAnime(anime);
        soundtrackRepository.save(soundtrack2);
        playlist.addSoundtrack(soundtrack1);
        playlist.addSoundtrack(soundtrack2);
        playlistRepository.save(playlist);
        Playlist createdPlaylist = playlistRepository.findById(playlist.getId()).get();
        assertThat(createdPlaylist.getSoundtracks()).hasSize(2);
        playlistRepository.deleteById(playlist.getId());
        Soundtrack afterDelete = soundtrackRepository.findById(1).get();
        assertThat(playlistRepository.findById(1)).isEmpty();
        assertThat(afterDelete.getPlaylists()).isNull();
        assertThat(animeRepository.findById(1)).isNotNull();
    }
}

