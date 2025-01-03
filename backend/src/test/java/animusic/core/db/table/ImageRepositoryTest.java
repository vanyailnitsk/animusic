package animusic.core.db.table;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
class ImageRepositoryTest extends DatabaseTest {

    @Autowired
    ImageRepository imageRepository;

    @Test
    void init() {
    }

}