package com.animusic.api.mappers;

import com.animusic.api.dto.CoverArtDto;
import com.animusic.core.db.model.CoverArt;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CoverArtMapper {

    @NonNull
    private ImageMapper imageMapper;

    public CoverArtDto fromCoverArt(CoverArt dao) {
        var image = imageMapper.fromImage(dao.getImage());
        return new CoverArtDto(image, dao.getColorLight(), dao.getColorDark());
    }
}
