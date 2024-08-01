package com.animusic.core.db.table;

import com.animusic.core.db.DatabaseTest;
import com.animusic.core.db.model.Playlist;
import com.animusic.core.db.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class UserRepositoryTest extends DatabaseTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PlaylistRepository playlistRepository;

    @Test
    void findByEmail() {
        assertThat(userRepository.findByEmail("email")).isEmpty();
        var user = User.builder()
                .email("email")
                .username("username")
                .password("password")
                .build();
        userRepository.save(user);
        assertThat(userRepository.findByEmail("email").get()).isEqualTo(user);
    }

    @Test
    void getUserFavouritePlaylist() {
        var user = User.builder()
                .email("email")
                .username("username")
                .password("password")
                .build();
        userRepository.save(user);
        var playlist = Playlist.builder()
                .name("Favourite tracks")
                .user(user)
                .build();
        playlistRepository.save(playlist);
        assertThat(userRepository.getUserFavouritePlaylist(user.getId()).get()).isEqualTo(playlist);
    }

    @Test
    void getUserFavouritePlaylistNotFound() {
        var user = User.builder()
                .email("email")
                .username("username")
                .password("password")
                .build();
        userRepository.save(user);
        var playlist = Playlist.builder()
                .name("My playlist")
                .user(user)
                .build();
        playlistRepository.save(playlist);
        assertThat(userRepository.getUserFavouritePlaylist(user.getId())).isEmpty();
    }
}