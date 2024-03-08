package com.ilnitsk.animusic.user.repository;

import com.ilnitsk.animusic.user.dao.UserPlaylistSoundtrack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPlaylistSoundtrackRepository extends JpaRepository<UserPlaylistSoundtrack,Long> {
}
