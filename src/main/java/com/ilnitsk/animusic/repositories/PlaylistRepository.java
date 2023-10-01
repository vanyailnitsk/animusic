package com.ilnitsk.animusic.repositories;

import com.ilnitsk.animusic.models.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistRepository extends JpaRepository<Playlist,Integer> {
}
