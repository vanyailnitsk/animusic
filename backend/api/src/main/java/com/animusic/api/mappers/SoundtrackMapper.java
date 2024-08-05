package com.animusic.api.mappers;

import java.util.List;

import com.animusic.api.dto.PlaylistSoundtrackDto;
import com.animusic.api.dto.SoundtrackDto;
import com.animusic.api.dto.SoundtrackEntityDto;
import com.animusic.content.soundtrack.SoundtrackSavedHelper;
import com.animusic.core.db.model.PlaylistSoundtrack;
import com.animusic.core.db.model.Soundtrack;
import org.springframework.stereotype.Component;

@Component
public class SoundtrackMapper {

    public SoundtrackDto fromSoundtrack(Soundtrack soundtrack) {
        return new SoundtrackDto(soundtrackEntity(soundtrack));
    }

    public SoundtrackEntityDto soundtrackEntity(Soundtrack soundtrack) {
        return new SoundtrackEntityDto(
                soundtrack.getId(),
                soundtrack.getOriginalTitle(),
                soundtrack.getAnimeTitle(),
                soundtrack.getAudioFile(),
                ImageMapper.fromImage(soundtrack.getImage()),
                soundtrack.getDuration(),
                SoundtrackSavedHelper.isSaved(soundtrack),
                AlbumMapper.albumItemDto(soundtrack.getAlbum()),
                AnimeMapper.animeItemDto(soundtrack.getAnime())
        );
    }

    public List<SoundtrackEntityDto> soundtracksList(List<Soundtrack> soundtracks) {
        return soundtracks.stream().map(SoundtrackMapper::soundtrackEntity).toList();
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
