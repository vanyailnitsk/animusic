package com.animusic.core.db;

import com.animusic.core.AnimusicApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig({
        AnimusicApplication.class,
        TestDbConfiguration.class
})
@ActiveProfiles("testing")
@Slf4j
public class DatabaseTest {

}
