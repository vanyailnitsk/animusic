package animusic.core.db.table;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import animusic.core.db.model.User;

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

}