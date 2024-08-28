package com.animusic.core.db.table;

import com.animusic.core.db.DatabaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class TrackListeningEventRepositoryTest extends DatabaseTest {

    @Autowired
    TrackListeningEventRepository listeningEventRepository;

    @Test
    void findAll() {
        assertThat(listeningEventRepository.findAll()).isNotNull();
    }

}