package com.ilnitsk.animusic.soundtrack.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.ilnitsk.animusic.exception.BadRequestException;
import com.ilnitsk.animusic.soundtrack.dao.Soundtrack;
import com.ilnitsk.animusic.soundtrack.dto.CreateSoundtrackDto;
import com.ilnitsk.animusic.soundtrack.dto.SoundtrackConverter;
import com.ilnitsk.animusic.soundtrack.dto.SoundtrackDto;
import com.ilnitsk.animusic.soundtrack.dto.UpdateSoundtrackDto;
import com.ilnitsk.animusic.soundtrack.service.SoundtrackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRange;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
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

    @PatchMapping("{soundtrackId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Метод для обновления саундтрека")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное обновление саундтрека"),
            @ApiResponse(responseCode = "404", description = "Саундтрек не найден"),
            @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера")
    })
    public SoundtrackDto patchSoundtrack(@RequestBody JsonNode jsonPatch,
                                         @PathVariable Integer soundtrackId) {
        Soundtrack soundtrackPatched = soundtrackService.updateSoundtrack(jsonPatch,soundtrackId);
        SoundtrackDto soundtrackDto = soundtrackConverter.convertToDto(soundtrackPatched);
        log.info("Soundtrack id={} updated successfully",soundtrackId);
        return soundtrackDto;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Метод для создания саундтрека")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное создание саундтрека"),
            @ApiResponse(responseCode = "400", description = "Саундтрек уже существует"),
            @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера")
    })
    public SoundtrackDto createFromFile(@RequestPart(value = "audio") MultipartFile audio,
                                     @RequestPart(value = "image",required = false) MultipartFile image,
                                     @ModelAttribute CreateSoundtrackDto request,
                                        @RequestParam("albumId") Integer albumId) {
        Soundtrack soundtrack = soundtrackConverter.convertToEntity(request);
        if (audio.isEmpty()) {
            throw new BadRequestException("No mp3-file provided");
        }
        soundtrack = soundtrackService.createSoundtrack(
                audio, image, soundtrack, albumId
        );
        return soundtrackConverter.convertToDto(soundtrack);
    }

    @PutMapping("/audio/{soundtrackId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
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

    @PutMapping("/images/{soundtrackId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
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
        log.info("Image of Soundtrack with id={} updated to '{}'",soundtrackId,soundtrack.getImage().getSource());
        return soundtrackDto;
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
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
