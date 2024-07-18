package com.animusic.core.db.table;

import java.util.List;

import com.animusic.core.db.model.Anime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimeRepository extends JpaRepository<Anime, Integer> {
    Anime findAnimeByTitle(String title);

    boolean existsAnimeByTitle(String title);

    List<Anime> findAllByOrderByTitle();
}
