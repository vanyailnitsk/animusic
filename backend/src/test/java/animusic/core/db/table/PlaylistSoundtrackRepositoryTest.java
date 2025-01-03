package animusic.core.db.table;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@Sql(scripts = "mandatory_inserts.sql")
class PlaylistSoundtrackRepositoryTest extends DatabaseTest {

    @Autowired
    PlaylistSoundtrackRepository playlistSoundtrackRepository;

    @Test
    void deleteTrackFromPlaylist() {
        assertThat(playlistSoundtrackRepository.findAll().size()).isEqualTo(2);
        playlistSoundtrackRepository.deleteTrackFromPlaylist(1, 1);
        assertThat(playlistSoundtrackRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    void deleteNotMemberTrackFromPlaylist() {
        assertThat(playlistSoundtrackRepository.findAll().size()).isEqualTo(2);
        playlistSoundtrackRepository.deleteTrackFromPlaylist(1, 777);
        assertThat(playlistSoundtrackRepository.findAll().size()).isEqualTo(2);
    }
}