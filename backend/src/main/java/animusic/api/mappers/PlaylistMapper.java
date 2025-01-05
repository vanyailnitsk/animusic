package animusic.api.mappers;

import org.springframework.stereotype.Component;

import animusic.api.dto.PlaylistDto;
import animusic.core.db.model.Playlist;
import animusic.core.db.model.User;

@Component
public class PlaylistMapper {

    public static PlaylistDto convertToDto(Playlist dao, User user) {
        var owner = UserMapper.playlistOwner(dao.getUser());
        var soundtracks = SoundtrackMapper.playlistSoundtracks(dao.getSoundtracks(), user);
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
