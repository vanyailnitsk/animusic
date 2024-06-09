package com.animusic.album.repository;

import com.animusic.album.dao.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AlbumRepository extends JpaRepository<Album,Integer> {
    Optional<List<Album>> getAlbumsByAnimeId(Integer animeId);
    @Query("SELECT COUNT(p) > 0 FROM Album p WHERE p.name = :albumName AND p.anime.id = :animeId")
    boolean existsByNameAndAnimeId(@Param("albumName") String albumName, @Param("animeId") Integer animeId);

}
