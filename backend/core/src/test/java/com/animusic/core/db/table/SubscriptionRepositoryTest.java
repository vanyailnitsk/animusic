package com.animusic.core.db.table;

import java.util.Date;
import java.util.List;

import com.animusic.core.db.DatabaseTest;
import com.animusic.core.db.model.Album;
import com.animusic.core.db.model.Anime;
import com.animusic.core.db.model.SubscriptionForAlbum;
import com.animusic.core.db.model.SubscriptionForAnime;
import com.animusic.core.db.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class SubscriptionRepositoryTest extends DatabaseTest {

    @Autowired
    SubscriptionForAlbumRepository subscriptionForAlbumRepository;

    @Autowired
    SubscriptionForAnimeRepository subscriptionForAnimeRepository;

    @Autowired
    AnimeRepository animeRepository;

    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    void findByUserId() {
        var anime = Anime.builder()
                .title("Anime-1")
                .folderName("anime-1")
                .build();
        animeRepository.save(anime);

        var album = Album.builder()
                .name("Openings")
                .anime(anime)
                .build();
        albumRepository.save(album);

        var user = User.builder()
                .email("email")
                .password("password")
                .build();
        userRepository.save(user);

        var subscribe1 = SubscriptionForAnime.builder()
                .user(user)
                .anime(anime)
                .addedAt(new Date())
                .build();

        var subscribe2 = SubscriptionForAlbum.builder()
                .user(user)
                .album(album)
                .addedAt(new Date())
                .build();

        subscriptionForAnimeRepository.save(subscribe1);
        subscriptionForAlbumRepository.save(subscribe2);

        assertThat(subscriptionForAnimeRepository.findByUserId(user.getId())).isEqualTo(List.of(subscribe1));
        assertThat(subscriptionForAlbumRepository.findByUserId(user.getId())).isEqualTo(List.of(subscribe2));
    }
}