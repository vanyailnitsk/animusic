package com.ilnitsk.animusic.user.repository;

import com.ilnitsk.animusic.user.dao.UserPlaylist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPlaylistRepository extends JpaRepository<UserPlaylist,Long> {
}
