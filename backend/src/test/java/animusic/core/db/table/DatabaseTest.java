package animusic.core.db.table;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import animusic.core.db.TestingDbConfig;

import static io.zonky.test.db.AutoConfigureEmbeddedDatabase.DatabaseProvider.EMBEDDED;
import static io.zonky.test.db.AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY;

@SpringJUnitConfig({
        TestingDbConfig.class
})
@AutoConfigureEmbeddedDatabase
@Slf4j
public class DatabaseTest {

}
