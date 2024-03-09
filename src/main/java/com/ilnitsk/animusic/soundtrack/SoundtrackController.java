package com.ilnitsk.animusic.soundtrack;

import com.ilnitsk.animusic.exception.BadRequestException;
import com.ilnitsk.animusic.s3.S3Service;
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
    private final S3Service s3Service;

    @GetMapping("/play/{trackId}")
    public ResponseEntity<StreamingResponseBody> playTrack(
            @PathVariable Integer trackId,
            @RequestHeader(value = HttpHeaders.RANGE, required = false) String range) {
        List<HttpRange> httpRangeList = HttpRange.parseRanges(range);
        log.info("Playing track: {}", trackId);
        return soundtrackService.getAudioStream(trackId, !httpRangeList.isEmpty() ? httpRangeList.get(0) : null);
    }

    @GetMapping("/audio")
    public ResponseEntity<StreamingResponseBody> getAudio(@RequestParam String trackName) {
       byte[] data = s3Service.getObject(trackName);
       StreamingResponseBody streamingResponseBody = out -> {
           out.write(data);
       };
       return ResponseEntity.ok(streamingResponseBody);
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
