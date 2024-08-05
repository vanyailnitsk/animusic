package com.animusic.api.mappers;

import com.animusic.api.dto.CoverArtDto;
import com.animusic.core.db.model.CoverArt;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;

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
