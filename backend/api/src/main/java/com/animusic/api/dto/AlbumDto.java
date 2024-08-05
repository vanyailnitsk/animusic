package com.animusic.api.dto;

import java.util.List;

import lombok.Builder;

@Builder
public record AlbumDto(
        Integer id,
        String name,
        AnimeItemDto anime,
        List<SoundtrackDto> soundtracks,
        CoverArtDto coverArt
) {
}
