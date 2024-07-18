package com.animusic.api.dto;

import com.animusic.core.db.model.Soundtrack;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SoundtrackDto {
    private SoundtrackEntityDto soundtrack;

    public static SoundtrackDto fromSoundtrack(Soundtrack soundtrack) {
        return new SoundtrackDto(SoundtrackEntityDto.fromSoundtrack(soundtrack));
    }
}
