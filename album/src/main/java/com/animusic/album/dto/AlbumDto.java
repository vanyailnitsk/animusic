package com.animusic.album.dto;

import com.ilnitsk.animusic.anime.dto.AnimeItemDto;
import com.ilnitsk.animusic.image.dto.CoverArtDto;
import com.ilnitsk.animusic.soundtrack.dto.SoundtrackDto;

import java.util.List;

public record AlbumDto(
        Integer id,
        String name,
        AnimeItemDto anime,
        String link,
        List<SoundtrackDto> soundtracks,
        CoverArtDto coverArt
){}
