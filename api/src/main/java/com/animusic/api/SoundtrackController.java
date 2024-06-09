package com.animusic.api;

import com.animusic.api.dto.SoundtrackDto;
import com.animusic.api.dto.UpdateSoundtrackDto;
import com.animusic.soundtrack.dao.Soundtrack;
import com.animusic.soundtrack.service.SoundtrackService;
import com.fasterxml.jackson.databind.JsonNode;
import com.ilnitsk.animusic.exception.BadRequestException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/soundtracks")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "REST API для управления саундтреками")
public class SoundtrackController {
    private final SoundtrackService soundtrackService;

    @GetMapping("{soundtrackId}")
    @Operation(summary = "Метод для получения саундтрека по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение саундтрека"),
            @ApiResponse(responseCode = "404", description = "Саундтрек не найден"),
            @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера")
    })
    public SoundtrackDto getSoundtrack(@PathVariable Integer soundtrackId) {
        Soundtrack soundtrack = soundtrackService.getSoundtrack(soundtrackId);
        return SoundtrackDto.fromSoundtrack(soundtrack);
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
        Soundtrack soundtrack = soundtrackService.updateSoundtrack(
                soundtrackId,
                updateSoundtrackDto.originalTitle(),
                updateSoundtrackDto.animeTitle(),
                updateSoundtrackDto.duration()
        );
        log.info("Soundtrack id={} updated successfully", soundtrackId);
        return SoundtrackDto.fromSoundtrack(soundtrack);
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
        Soundtrack soundtrackPatched = soundtrackService.updateSoundtrack(jsonPatch, soundtrackId);
        log.info("Soundtrack id={} updated successfully", soundtrackId);
        return SoundtrackDto.fromSoundtrack(soundtrackPatched);
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
                                        @RequestPart(value = "image", required = false) MultipartFile image,
                                        @ModelAttribute CreateSoundtrackDto request,
                                        @RequestParam("albumId") Integer albumId) {
        Soundtrack soundtrack = request.toSoundtrack();
        if (audio.isEmpty()) {
            throw new BadRequestException("No mp3-file provided");
        }
        soundtrack = soundtrackService.createSoundtrack(
                audio, image, soundtrack, albumId
        );
        return SoundtrackDto.fromSoundtrack(soundtrack);
    }

    @PutMapping("/audio/{soundtrackId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Метод для обновления аудиофайла у саундтрека")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное обновления саундтрека"),
            @ApiResponse(responseCode = "404", description = "Саундтрек не найден"),
            @ApiResponse(responseCode = "400", description = "Пустой аудиофайл")
    })
    public SoundtrackDto updateAudioFile(@RequestPart("audio") MultipartFile audio, @PathVariable Integer soundtrackId) {
        Soundtrack soundtrack = soundtrackService.updateAudio(audio, soundtrackId);
        log.info("Soundtrack id={} audio updated to {}", soundtrackId, soundtrack.getAudioFile());
        return SoundtrackDto.fromSoundtrack(soundtrack);
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
        Soundtrack soundtrack = soundtrackService.setImage(soundtrackId, image);
        log.info("Image of Soundtrack with id={} updated to '{}'", soundtrackId, soundtrack.getImage().getSource());
        return SoundtrackDto.fromSoundtrack(soundtrack);
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

    record CreateSoundtrackDto(
            String originalTitle,
            String animeTitle,
            Integer duration
    ) {
        public Soundtrack toSoundtrack() {
            return Soundtrack.builder()
                    .originalTitle(originalTitle)
                    .animeTitle(animeTitle)
                    .duration(duration)
                    .build();
        }
    }
}
