package com.animusic.core.db.table;

import com.animusic.core.db.DatabaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
class CoverArtRepositoryTest extends DatabaseTest {

    @Autowired
    CoverArtRepository coverArtRepository;

    @Test
    void init() {
    }

}