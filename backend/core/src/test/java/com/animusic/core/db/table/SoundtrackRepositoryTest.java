package com.animusic.core.db.table;

import com.animusic.core.AnimusicApplication;
import com.animusic.core.conf.DatabaseConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

@SpringJUnitConfig({
        AnimusicApplication.class,
        DatabaseConfig.class
})
@Transactional
class SoundtrackRepositoryTest {

    @Autowired
    SoundtrackRepository soundtrackRepository;

    @Test
    void init() {
    }

}