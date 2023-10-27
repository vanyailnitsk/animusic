package com.ilnitsk.animusic.repositories;

import com.ilnitsk.animusic.models.Anime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimeRepository extends JpaRepository<Anime, Integer> {
    Anime findAnimeByTitle(String title);
    boolean existsAnimeByTitle(String title);
}
