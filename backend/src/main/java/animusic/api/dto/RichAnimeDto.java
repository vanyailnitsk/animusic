package animusic.api.dto;

import java.util.List;

public record RichAnimeDto(
        Integer id,
        String title,
        String studio,
        Integer releaseYear,
        String description,
        String folderName,
        AnimeBannerImageDto banner,
        ImageDto cardImage,
        Boolean isSubscribed,
        List<AlbumItemDto> albums
) {
}
