package com.ilnitsk.animusic.repositories;

import com.ilnitsk.animusic.models.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PlaylistRepository extends JpaRepository<Playlist,Integer> {
    List<Playlist> getPlaylistsByAnimeId(Integer animeId);
}
