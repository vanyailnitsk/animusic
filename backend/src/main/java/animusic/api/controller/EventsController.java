package animusic.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import animusic.service.security.UserService;
import animusic.service.soundtrack.EventsService;

@RestController
@RequestMapping("/api/events")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Events management")
public class EventsController {

    private final EventsService eventsService;

    private final UserService userService;

    @PostMapping("listening")
    @Operation(summary = "Начато проигрывание трека")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Событие записано"),
            @ApiResponse(responseCode = "404", description = "Саундтрек не найден")
    })
    public void trackListeningEvent(
            @RequestBody ListeningEventDto event
    ) {
        var user = userService.getUserInSession().orElse(null);
        eventsService.addListeningEvent(user, event.trackId());
    }

    public record ListeningEventDto(
            Integer trackId
    ) {
    }

}
