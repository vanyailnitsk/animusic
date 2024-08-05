package com.animusic.api.mappers;

import com.animusic.api.dto.PlaylistDto;
import com.animusic.core.db.model.Playlist;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlaylistMapper {

    @NonNull
    private UserMapper userMapper;

    @NonNull
    private SoundtrackMapper soundtrackMapper;

    @NonNull
    private CoverArtMapper coverArtMapper;

    public PlaylistDto convertToDto(Playlist dao) {
        var owner = userMapper.playlistOwner(dao.getUser());
        var soundtracks = soundtrackMapper.playlistSoundtracks(dao.getSoundtracks());
        var coverArt = coverArtMapper.fromCoverArt(dao.getCoverArt());
        return new PlaylistDto(
                dao.getId(),
                dao.getName(),
                owner,
                soundtracks,
                coverArt
        );
    }

}
