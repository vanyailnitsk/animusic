package com.animusic.playlist.repository;

import com.animusic.playlist.dao.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist,Integer> {
}
