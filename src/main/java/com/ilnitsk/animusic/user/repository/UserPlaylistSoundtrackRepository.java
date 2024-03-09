package com.ilnitsk.animusic.user.repository;

import com.ilnitsk.animusic.user.dao.UserPlaylistSoundtrack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPlaylistSoundtrackRepository extends JpaRepository<UserPlaylistSoundtrack,Long> {
    @Query("SELECT COUNT(s) > 0 FROM UserPlaylistSoundtrack s where s.playlist.id= :playlist_id and s.soundtrack.id= :soundtrack_id")
    boolean playlistAlreadyContainsSoundtrack(
            @Param("playlist_id") Long playlist_id, @Param("soundtrack_id") Integer soundtrack_id);
}
