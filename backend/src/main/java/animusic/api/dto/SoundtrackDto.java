package animusic.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SoundtrackDto {
    private SoundtrackEntityDto soundtrack;

    public static SoundtrackDto fromSoundtrack(SoundtrackEntityDto soundtrack) {
        return new SoundtrackDto(soundtrack);
    }
}
