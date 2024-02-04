package com.ilnitsk.animusic.soundtrack;

import com.ilnitsk.animusic.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRange;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.util.List;

@RestController
@RequestMapping("/api/soundtracks")
@Slf4j
public class SoundtrackController {
    private final SoundtrackService soundtrackService;

    @Autowired
    public SoundtrackController(SoundtrackService soundtrackService) {
        this.soundtrackService = soundtrackService;
    }

    @GetMapping("/play/{trackId}")
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

    @GetMapping("/images/{soundtrackId}")
    public ResponseEntity<byte[]> getSoundtrackImage(@PathVariable Integer soundtrackId) {
        return soundtrackService.getSoundtrackImage(soundtrackId);
    }

    @PostMapping
    public Soundtrack createFromFile(@RequestPart(value = "file") MultipartFile audio,
                                     @ModelAttribute SoundtrackRequest request) {
        if (audio.isEmpty()) {
            throw new BadRequestException("No mp3-file provided");
        }
        return soundtrackService.createSoundtrack(
                audio, request.createSoundtrack(), request.getPlaylistId()
        );
    }

    @DeleteMapping("{id}")
    public void deleteSoundtrack(@PathVariable Integer id) {
        soundtrackService.remove(id);
    }
}
