package animusic.api.dto;

public record UpdateSoundtrackDto(
        String originalTitle,
        String animeTitle,
        Integer duration
) {
}
