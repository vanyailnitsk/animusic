package com.ilnitsk.animusic.repositories;

import com.ilnitsk.animusic.models.Anime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnimeRepository extends JpaRepository<Anime, Integer> {
    Anime findAnimeByTitle(String title);
    boolean existsAnimeByTitle(String title);

    List<Anime> findAllByOrderByTitle();
}
