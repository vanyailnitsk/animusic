package com.ilnitsk.animusic.anime;

import com.ilnitsk.animusic.anime.Anime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimeRepository extends JpaRepository<Anime, Integer> {
    Anime findAnimeByTitle(String title);
    boolean existsAnimeByTitle(String title);

    List<Anime> findAllByOrderByTitle();
}
