package com.animusic.api.mappers;

import com.animusic.api.AlbumCoverController;
import com.animusic.api.dto.CoverArtDto;
import com.animusic.core.db.model.CoverArt;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CoverArtConverter {
    private final ModelMapper modelMapper;

    public CoverArtConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        modelMapper.createTypeMap(AlbumCoverController.CreateCoverDto.class, CoverArt.class);
    }

    public CoverArt convertToEntity(AlbumCoverController.CreateCoverDto dto) {
        return modelMapper.map(dto, CoverArt.class);
    }

    public CoverArtDto convertToDto(CoverArt dao) {
        return modelMapper.map(dao, CoverArtDto.class);
    }
}
