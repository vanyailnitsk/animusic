package com.animusic.api.controller;

import java.util.List;

import com.animusic.api.dto.SoundtrackDto;
import com.animusic.api.mappers.SoundtrackMapper;
import com.animusic.content.analytics.StatisticsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stats")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Statistics controller")
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/soundtracks/most-popular")
    public List<SoundtrackDto> mostPopularTracks(
            @RequestParam(required = false, defaultValue = "5") Integer limit
    ) {
        var soundtracks = statisticsService.mostPopularTracks(limit);
        return SoundtrackMapper.soundtrackDtos(soundtracks);
    }


}
