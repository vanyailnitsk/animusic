package com.ilnitsk.animusic.repositories;

import com.ilnitsk.animusic.models.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PlaylistRepository extends JpaRepository<Playlist,Integer> {
    Optional<List<Playlist>> getPlaylistsByAnimeId(Integer animeId);
}
