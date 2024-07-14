package com.animusic.api.dto;

import java.util.List;

public record AlbumDto(
        Integer id,
        String name,
        AnimeItemDto anime,
        String link,
        List<SoundtrackDto> soundtracks,
        CoverArtDto coverArt
){}
