package com.ilnitsk.animusic.soundtrack.dto;

import com.ilnitsk.animusic.soundtrack.Soundtrack;
import com.ilnitsk.animusic.soundtrack.SoundtrackDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class SoundtrackConverter {
    private final ModelMapper modelMapper;

    public SoundtrackConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public SoundtrackDto convertToDto(Soundtrack soundtrack) {
        return modelMapper.map(soundtrack,SoundtrackDto.class);
    }
}
