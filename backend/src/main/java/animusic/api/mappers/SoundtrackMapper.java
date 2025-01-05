package animusic.api.mappers;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;

import animusic.api.dto.PlaylistSoundtrackDto;
import animusic.api.dto.SoundtrackDto;
import animusic.api.dto.SoundtrackEntityDto;
import animusic.core.db.model.Image;
import animusic.core.db.model.PlaylistSoundtrack;
import animusic.core.db.model.Soundtrack;
import animusic.core.db.model.User;
import animusic.service.soundtrack.SoundtrackSavedHelper;
import animusic.util.StoragePathResolver;

@Component
public class SoundtrackMapper {

    public static SoundtrackDto fromSoundtrack(Soundtrack soundtrack, User currentUser) {
        return new SoundtrackDto(soundtrackEntity(soundtrack, currentUser));
    }

    public static SoundtrackEntityDto soundtrackEntity(Soundtrack soundtrack, User currentUser) {
        var image = Objects.requireNonNullElse(soundtrack.getImage(), defaultSoundtrackImage());
        return new SoundtrackEntityDto(
                soundtrack.getId(),
                soundtrack.getOriginalTitle(),
                soundtrack.getAnimeTitle(),
                StoragePathResolver.getAbsoluteFileUrl(soundtrack.getAudioFile()),
                ImageMapper.fromImage(image),
                soundtrack.getDuration(),
                SoundtrackSavedHelper.isSaved(soundtrack, currentUser),
                AlbumMapper.albumItemDto(soundtrack.getAlbum()),
                AnimeMapper.animeItemDto(soundtrack.getAnime())
        );
    }

    public static List<SoundtrackEntityDto> soundtracksList(List<Soundtrack> soundtracks, User currentUser) {
        return soundtracks.stream().map(s -> soundtrackEntity(s, currentUser)).toList();
    }

    public static List<SoundtrackDto> soundtrackDtos(List<Soundtrack> soundtracks, User currentUser) {
        return soundtracksList(soundtracks, currentUser).stream().map(SoundtrackDto::new).toList();
    }

    public static List<PlaylistSoundtrackDto> playlistSoundtracks(List<PlaylistSoundtrack> playlistSoundtracks, User currentUser) {
        return playlistSoundtracks.stream()
                .map(ps -> new PlaylistSoundtrackDto(ps.getAddedAt(), soundtrackEntity(ps.getSoundtrack(), currentUser)))
                .toList();
    }

    private static Image defaultSoundtrackImage() {
        return Image.builder().source("images/track-img.jpeg").build();
    }
}
