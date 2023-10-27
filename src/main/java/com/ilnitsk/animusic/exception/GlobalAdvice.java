package com.ilnitsk.animusic.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalAdvice {
    @ResponseBody
    @ExceptionHandler(PlaylistNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String playlistNotFoundHandler(PlaylistNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(AnimeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String animeNotFound(AnimeNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(SoundtrackNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String soundtrackNotFound(SoundtrackNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String badRequest(BadRequestException ex) {
        return ex.getMessage();
    }


}
