package animusic.core.db.table;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
class AnimeBannerImageRepositoryTest extends DatabaseTest {

    @Autowired
    AnimeBannerImageRepository animeBannerImageRepository;

    @Test
    void init() {
    }

}