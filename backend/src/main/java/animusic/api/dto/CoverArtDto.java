package animusic.api.dto;

import lombok.Value;

@Value
public class CoverArtDto {
    Colors colors;

    ImageDto image;

    public CoverArtDto(ImageDto image, String colorLight, String colorDark) {
        this.image = image;
        this.colors = new Colors(colorLight, colorDark);
    }

    @Value
    public static class Colors {
        String colorLight;
        String colorDark;
    }
}
