package animusic.api.mappers;

import org.springframework.stereotype.Component;

import animusic.api.dto.PlaylistDto;
import animusic.core.db.model.Playlist;

@Component
public class PlaylistMapper {

    public static PlaylistDto convertToDto(Playlist dao) {
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
