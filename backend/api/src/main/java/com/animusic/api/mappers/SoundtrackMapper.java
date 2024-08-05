package com.animusic.api.mappers;

import java.util.List;

import com.animusic.api.dto.PlaylistSoundtrackDto;
import com.animusic.api.dto.SoundtrackDto;
import com.animusic.api.dto.SoundtrackEntityDto;
import com.animusic.content.soundtrack.SoundtrackSavedHelper;
import com.animusic.core.db.model.PlaylistSoundtrack;
import com.animusic.core.db.model.Soundtrack;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SoundtrackMapper {

    @NonNull
    private ImageMapper imageMapper;

    @NonNull
    private AlbumMapper albumMapper;

    @NonNull
    private AnimeMapper animeMapper;

    public SoundtrackDto fromSoundtrack(Soundtrack soundtrack) {
        return new SoundtrackDto(soundtrackEntity(soundtrack));
    }

    public SoundtrackEntityDto soundtrackEntity(Soundtrack soundtrack) {
        return new SoundtrackEntityDto(
                soundtrack.getId(),
                soundtrack.getOriginalTitle(),
                soundtrack.getAnimeTitle(),
                soundtrack.getAudioFile(),
                imageMapper.fromImage(soundtrack.getImage()),
                soundtrack.getDuration(),
                SoundtrackSavedHelper.isSaved(soundtrack),
                albumMapper.albumItemDto(soundtrack.getAlbum()),
                animeMapper.animeItemDto(soundtrack.getAnime())
        );
    }

    public List<SoundtrackEntityDto> soundtracksList(List<Soundtrack> soundtracks) {
        return soundtracks.stream().map(this::soundtrackEntity).toList();
    }

    public List<SoundtrackDto> soundtrackDtos(List<Soundtrack> soundtracks) {
        return soundtracksList(soundtracks).stream().map(SoundtrackDto::new).toList();
    }

    public List<PlaylistSoundtrackDto> playlistSoundtracks(List<PlaylistSoundtrack> playlistSoundtracks) {
        return playlistSoundtracks.stream()
                .map(ps -> new PlaylistSoundtrackDto(ps.getAddedAt(), soundtrackEntity(ps.getSoundtrack())))
                .toList();
    }
}
