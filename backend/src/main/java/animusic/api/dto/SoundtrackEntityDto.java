package animusic.api.dto;

public record SoundtrackEntityDto(
        Integer id,
        String originalTitle,
        String animeTitle,
        String audioFile,
        ImageDto image,
        Integer duration,
        boolean saved,
        AlbumItemDto album,
        AnimeItemDto anime
) {
}
