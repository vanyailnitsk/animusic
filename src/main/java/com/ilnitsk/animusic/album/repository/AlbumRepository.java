package com.ilnitsk.animusic.album.repository;

import com.ilnitsk.animusic.album.dao.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AlbumRepository extends JpaRepository<Album,Integer> {
    Optional<List<Album>> getPlaylistsByAnimeId(Integer animeId);
    @Query("SELECT COUNT(p) > 0 FROM Album p WHERE p.name = :playlistName AND p.anime.id = :animeId")
    boolean existsByNameAndAnimeId(@Param("playlistName") String playlistName, @Param("animeId") Integer animeId);

}
