package com.ilnitsk.animusic.soundtrack;

import com.ilnitsk.animusic.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRange;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.util.List;

@RestController
@RequestMapping("/api/soundtracks")
@RequiredArgsConstructor
@Slf4j
public class SoundtrackController {
    private final SoundtrackService soundtrackService;

    @GetMapping("/play/{trackId}")
    @Deprecated
    public ResponseEntity<StreamingResponseBody> playTrack(
            @PathVariable Integer trackId,
            @RequestHeader(value = HttpHeaders.RANGE, required = false) String range) {
        List<HttpRange> httpRangeList = HttpRange.parseRanges(range);
        log.info("Playing track: {}", trackId);
        return soundtrackService.getAudioStream(trackId, !httpRangeList.isEmpty() ? httpRangeList.get(0) : null);
    }

    @GetMapping("{soundtrackId}")
    public Soundtrack getSoundtrack(@PathVariable Integer soundtrackId) {
        return soundtrackService.getSoundtrack(soundtrackId);
    }

    @PostMapping
    public Soundtrack createFromFile(@RequestPart(value = "audio") MultipartFile audio,
                                     @RequestPart(value = "image",required = false) MultipartFile image,
                                     @ModelAttribute SoundtrackRequest request) {
        if (audio.isEmpty()) {
            throw new BadRequestException("No mp3-file provided");
        }
        return soundtrackService.createSoundtrack(
                audio, image, request.createSoundtrack(), request.getPlaylistId()
        );
    }

    @GetMapping("/images/{soundtrackId}")
    @Deprecated
    public ResponseEntity<byte[]> getSoundtrackImage(@PathVariable Integer soundtrackId) {
        return soundtrackService.getSoundtrackImage(soundtrackId);
    }

    @PostMapping("/images/{soundtrackId}")
    public void setSoundtrackImage(@PathVariable Integer soundtrackId,
                          @RequestPart(value = "image") MultipartFile image) {
        soundtrackService.setImage(soundtrackId,image);
    }

    @PutMapping("/update-duration")
    public void updateSoundtracksDuration() {
        soundtrackService.updateAllTracksDuration();
    }

    @DeleteMapping("{id}")
    public void deleteSoundtrack(@PathVariable Integer id) {
        soundtrackService.remove(id);
    }
}
