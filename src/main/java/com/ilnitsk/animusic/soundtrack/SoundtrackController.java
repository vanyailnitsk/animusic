package com.ilnitsk.animusic.soundtrack;

import com.ilnitsk.animusic.exception.BadRequestException;
import com.ilnitsk.animusic.soundtrack.dto.SoundtrackConverter;
import com.ilnitsk.animusic.soundtrack.dto.SoundtrackDto;
import com.ilnitsk.animusic.soundtrack.dto.SoundtrackRequest;
import com.ilnitsk.animusic.soundtrack.dto.UpdateSoundtrackDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "REST API для управления саундтреками", description = "Предоставляет методы для управления саундтреками")
public class SoundtrackController {
    private final SoundtrackService soundtrackService;
    private final SoundtrackConverter soundtrackConverter;

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
    @Operation(summary = "Метод для получения саундтрека по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение саундтрека"),
            @ApiResponse(responseCode = "404", description = "Саундтрек не найден"),
            @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера")
    })
    public SoundtrackDto getSoundtrack(@PathVariable Integer soundtrackId) {
        Soundtrack soundtrack = soundtrackService.getSoundtrack(soundtrackId);
        return soundtrackConverter.convertToDto(soundtrack);
    }

    @PutMapping("{soundtrackId}")
    @Operation(summary = "Метод для обновления саундтрека")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное обновление саундтрека"),
            @ApiResponse(responseCode = "404", description = "Саундтрек не найден"),
            @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера")
    })
    public SoundtrackDto updateSoundtrack(@RequestBody UpdateSoundtrackDto updateSoundtrackDto,
                                          @PathVariable Integer soundtrackId) {
        Soundtrack soundtrack = soundtrackService.updateSoundtrack(updateSoundtrackDto,soundtrackId);
        SoundtrackDto soundtrackDto = soundtrackConverter.convertToDto(soundtrack);
        log.info("Soundtrack id={} updated successfully",soundtrackId);
        return soundtrackDto;
    }

    @PostMapping
    @Operation(summary = "Метод для создания саундтрека")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное создание саундтрека"),
            @ApiResponse(responseCode = "400", description = "Саундтрек уже существует"),
            @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера")
    })
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

    @PutMapping("/audio/{soundtrackId}")
    @Operation(summary = "Метод для обновления аудиофайла у саундтрека")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное обновления саундтрека"),
            @ApiResponse(responseCode = "404", description = "Саундтрек не найден"),
            @ApiResponse(responseCode = "400", description = "Пустой аудиофайл")
    })
    public SoundtrackDto updateAudioFile(@RequestPart("audio") MultipartFile audio,@PathVariable Integer soundtrackId) {
        Soundtrack soundtrack = soundtrackService.updateAudio(audio,soundtrackId);
        SoundtrackDto soundtrackDto = soundtrackConverter.convertToDto(soundtrack);
        log.info("Soundtrack id={} audio updated to {}",soundtrackId,soundtrack.getAudioFile());
        return soundtrackDto;
    }

    @GetMapping("/images/{soundtrackId}")
    @Deprecated
    public ResponseEntity<byte[]> getSoundtrackImage(@PathVariable Integer soundtrackId) {
        return soundtrackService.getSoundtrackImage(soundtrackId);
    }

    @PutMapping("/images/{soundtrackId}")
    @Operation(summary = "Метод для установки изображения саундтрека")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешная установка изображения"),
            @ApiResponse(responseCode = "404", description = "Саундтрек не найден"),
            @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера")
    })
    public SoundtrackDto setSoundtrackImage(@PathVariable Integer soundtrackId,
                          @RequestPart(value = "image") MultipartFile image) {
        Soundtrack soundtrack = soundtrackService.setImage(soundtrackId,image);
        SoundtrackDto soundtrackDto = soundtrackConverter.convertToDto(soundtrack);
        log.info("Image of soundtrack with id={} updated to {}",soundtrackId,soundtrack.getImageFile());
        return soundtrackDto;
    }

    @PutMapping("/update-duration")
    @Operation(summary = "Метод для изменения длительности саундтрека")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное изменение длительности"),
            @ApiResponse(responseCode = "404", description = "Саундтрек не найден"),
            @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера")
    })
    public void updateSoundtracksDuration() {
        soundtrackService.updateAllTracksDuration();
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Метод для удаления саундтрека по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное удаления саундтрека"),
            @ApiResponse(responseCode = "404", description = "Саундтрек не найден"),
            @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера")
    })
    public void deleteSoundtrack(@PathVariable Integer id) {
        soundtrackService.remove(id);
    }
}
