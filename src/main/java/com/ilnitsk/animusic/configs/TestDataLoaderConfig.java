package com.ilnitsk.animusic.configs;

import com.ilnitsk.animusic.models.Anime;
import com.ilnitsk.animusic.models.Soundtrack;
import com.ilnitsk.animusic.repositories.AnimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Year;

@Configuration
public class TestDataLoaderConfig {

    private final AnimeRepository animeRepository;

    @Autowired
    public TestDataLoaderConfig(AnimeRepository animeRepository) {
        this.animeRepository = animeRepository;
    }


    public CommandLineRunner testDataLoader() {
        return args -> {
            Anime anime1 = Anime.builder()
                    .title("Hunter x Hunter")
                    .folderName("Hunter_x_Hunter")
                    .releaseYear(Year.of(2011))
                    .build();
            Anime anime2 = Anime.builder()
                    .title("Naruto Shippuden")
                    .folderName("Naruto_Shippuden")
                    .releaseYear(Year.of(2007))
                    .build();
            Anime anime3 = Anime.builder()
                    .title("Attack on Titan")
                    .folderName("Attack_on_Titan")
                    .releaseYear(Year.of(2013))
                    .build();
            animeRepository.save(anime1);
            animeRepository.save(anime2);
            animeRepository.save(anime3);
        };
    }
}
