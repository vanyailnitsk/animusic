package com.ilnitsk.animusic.soundtrack.dto;

import com.ilnitsk.animusic.soundtrack.dao.Soundtrack;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class SoundtrackConverter {
    private final ModelMapper modelMapper;

    public SoundtrackConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        modelMapper.createTypeMap(Soundtrack.class,SoundtrackEntityDto.class)
                .addMappings(mapper -> mapper.map(Soundtrack::getAlbum,SoundtrackEntityDto::setAlbum));

        modelMapper.createTypeMap(SoundtrackEntityDto.class,SoundtrackDto.class)
                .addMappings(mapper -> mapper.map(src -> src,SoundtrackDto::setSoundtrack));
        modelMapper.createTypeMap(Soundtrack.class,SoundtrackDto.class)
                .addMappings(mapper -> mapper.map(src -> src,SoundtrackDto::setSoundtrack));
    }

    public SoundtrackDto convertToDto(Soundtrack soundtrack) {
        return modelMapper.map(soundtrack,SoundtrackDto.class);
    }
}
