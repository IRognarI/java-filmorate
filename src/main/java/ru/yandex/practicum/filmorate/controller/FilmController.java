package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmServiceImpl;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController {
    private final InMemoryFilmStorage inMemoryFilmStorage;
    private final FilmServiceImpl filmService;

    @PostMapping
    public Film addFilm(@RequestBody @Valid Film filmObject) {
        return inMemoryFilmStorage.addFilm(filmObject);
    }

    @PutMapping
    public Film updateFilm(@RequestBody @Valid Film filmObject) {
        return inMemoryFilmStorage.updateFilm(filmObject);
    }

    @GetMapping
    public Collection<Film> getFilms() {
        return inMemoryFilmStorage.getFilms();
    }

    @DeleteMapping
    public void deleteAllFilms() {
        inMemoryFilmStorage.deleteAllFilms();
    }
}
