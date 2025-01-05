package animusic.api.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import animusic.api.dto.SoundtrackDto;
import animusic.api.mappers.SoundtrackMapper;
import animusic.core.db.model.User;
import animusic.service.analytics.StatisticsService;
import animusic.service.anime.AnimeNotFoundException;
import animusic.service.anime.AnimeService;

@RestController
@RequestMapping("/api/stats")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Statistics controller")
public class StatisticsController {

    private final StatisticsService statisticsService;

    private final AnimeService animeService;

    @GetMapping("/soundtracks/most-popular")
    public List<SoundtrackDto> mostPopularTracks(
            @AuthenticationPrincipal User user,
            @RequestParam(required = false, defaultValue = "5")
            @Schema(description = "Ограничение по количеству", defaultValue = "5") Integer limit
    ) {
        var soundtracks = statisticsService.mostPopularTracks(limit);
        return SoundtrackMapper.soundtrackDtos(soundtracks, user);
    }

    @GetMapping("/soundtracks/most-popular-by-anime")
    @Operation(summary = "Топ популярных треков из аниме")
    public List<SoundtrackDto> mostPopularAnimeTracks(
            @AuthenticationPrincipal User user,
            @RequestParam Integer animeId,
            @RequestParam(required = false, defaultValue = "5")
            @Schema(description = "Ограничение по количеству", defaultValue = "5") Integer limit
    ) {
        if (animeService.getAnime(animeId).isEmpty()) {
            throw new AnimeNotFoundException(animeId);
        }
        var soundtracks = statisticsService.mostPopularAnimeTracks(animeId, limit);
        return SoundtrackMapper.soundtrackDtos(soundtracks, user);
    }


}
