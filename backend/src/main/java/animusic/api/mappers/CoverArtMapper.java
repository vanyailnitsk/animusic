package animusic.api.mappers;

import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;

import animusic.api.dto.CoverArtDto;
import animusic.core.db.model.CoverArt;

@Component
public class CoverArtMapper {

    @Nullable
    public static CoverArtDto fromCoverArt(@Nullable CoverArt dao) {
        if (dao == null) {
            return null;
        }
        var image = ImageMapper.fromImage(dao.getImage());
        return new CoverArtDto(image, dao.getColorLight(), dao.getColorDark());
    }
}
