package com.animusic.api.mappers;

import com.animusic.api.dto.CoverArtDto;
import com.animusic.core.db.model.CoverArt;
import org.springframework.stereotype.Component;

@Component
public class CoverArtMapper {

    public CoverArtDto fromCoverArt(CoverArt dao) {
        var image = ImageMapper.fromImage(dao.getImage());
        return new CoverArtDto(image, dao.getColorLight(), dao.getColorDark());
    }
}
