package com.ilnitsk.animusic.soundtrack.dto;

import com.ilnitsk.animusic.soundtrack.Soundtrack;
import com.ilnitsk.animusic.soundtrack.SoundtrackDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class SoundtrackConverter {
    private final ModelMapper modelMapper;

    public SoundtrackConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        modelMapper.createTypeMap(Soundtrack.class,SoundtrackDto.class)
                .addMappings(mapper -> mapper.map(src -> src,SoundtrackDto::setSoundtrack));
    }

    public SoundtrackDto convertToDto(Soundtrack soundtrack) {
        return modelMapper.map(soundtrack,SoundtrackDto.class);
    }
}
