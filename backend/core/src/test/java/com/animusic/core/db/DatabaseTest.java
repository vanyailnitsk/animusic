package com.animusic.core.db;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static io.zonky.test.db.AutoConfigureEmbeddedDatabase.DatabaseProvider.DOCKER;

@SpringJUnitConfig({
        TestingDbConfig.class
})
@AutoConfigureEmbeddedDatabase(provider = DOCKER)
@Slf4j
public class DatabaseTest {

}
