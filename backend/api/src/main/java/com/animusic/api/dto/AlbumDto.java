package com.animusic.api.dto;

import java.util.List;

import lombok.Builder;

@Builder
public record AlbumDto(
        Integer id,
        String name,
        AnimeItemDto anime,
        CoverArtDto coverArt,
        Boolean isSubscribed,
        List<SoundtrackDto> soundtracks
) {
}
