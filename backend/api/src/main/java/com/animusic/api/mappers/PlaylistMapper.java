package com.animusic.api.mappers;

import com.animusic.api.dto.PlaylistDto;
import com.animusic.core.db.model.Playlist;
import org.springframework.stereotype.Component;

@Component
public class PlaylistMapper {

    public PlaylistDto convertToDto(Playlist dao) {
        var owner = UserMapper.playlistOwner(dao.getUser());
        var soundtracks = SoundtrackMapper.playlistSoundtracks(dao.getSoundtracks());
        var coverArt = CoverArtMapper.fromCoverArt(dao.getCoverArt());
        return new PlaylistDto(
                dao.getId(),
                dao.getName(),
                owner,
                soundtracks,
                coverArt
        );
    }

}
