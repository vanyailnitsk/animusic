package com.animusic.anime.service;

import com.animusic.core.db.table.AnimeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig({
        AnimeRepository.class
})
@ContextConfiguration(
        classes = AnimeRepository.class
)
@DataJpaTest
class AnimeServiceTest {

//    @Autowired
//    private AnimeRepository animeRepository;

    @Test
    void getAllAnime() {
//        animeRepository.findAll();
    }
}