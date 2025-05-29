package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.interfaces.FilmService;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Film addFilm(@RequestBody @Valid Film filmObject) {
        return filmService.addFilm(filmObject);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Film updateFilm(@RequestBody @Valid Film filmObject) {
        return filmService.updateFilm(filmObject);
    }

    @PutMapping("/{id}/like/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Long addLike(@PathVariable(name = "id") Long filmId,
                        @PathVariable(name = "userId") Long userId) {

        return filmService.addLike(filmId, userId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<Film> getFilms() {
        return filmService.getFilms();
    }

    @GetMapping("/popular")
    public Collection<Film> topOfBestFilms(@RequestParam(required = false, name = "count",
            defaultValue = "10") Integer count) {
        return filmService.topOfBestFilms(count);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllFilms() {
        filmService.deleteAllFilms();
    }

    @DeleteMapping("/{id}/like/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Long removeLike(@PathVariable(name = "id") Long filmId,
                           @PathVariable(name = "userId") Long userId) {

        return filmService.removeLike(filmId, userId);
    }
}