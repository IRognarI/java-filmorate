package ru.yandex.practicum.filmorate.controller;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import jakarta.validation.Valid;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.DuplicatedException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

@RestController
@RequestMapping("/films")
public class FilmController {
    private final Logger LOG = (Logger) LoggerFactory.getLogger(FilmController.class);

    private Film film;
    private final Map<Long, Film> filmMap = new HashMap<>();

    private Long nextID() {
        long ID = filmMap.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++ID;
    }

    @PostMapping
    public Object addFilm(@RequestBody @Valid Film filmObject) {
        LOG.setLevel(Level.DEBUG);

        if (filmObject == null) {
            throw new ValidationException("Не корректная инициализация объекта типа \"Film\"");
        }

        boolean nameFilmExists = filmMap.values()
                .stream()
                .map(f -> f.getName().trim())
                .anyMatch(f -> f.equalsIgnoreCase(filmObject.getName().trim()));

        if (nameFilmExists) {
            throw new DuplicatedException("Кино уже было добавлено");
        }

        LOG.debug("Получено ID: {}", filmObject.getID());
        LOG.debug("Получено название: {}", filmObject.getName().trim());
        LOG.debug("Получено описание: {}", filmObject.getDescription().trim());
        LOG.debug("Получена дата: ", filmObject.getReleaseDate());
        LOG.debug("Получена продолжительность: {}", filmObject.getDuration());

        film = new Film();

        film.setID(nextID());
        film.setName(film.validationName(filmObject.getName()));
        film.setDescription(film.validationDescription(filmObject.getDescription()));
        film.setReleaseDate(film.validationReleaseDate(filmObject.getReleaseDate()));
        film.setDuration(filmObject.getDuration());

        filmMap.put(film.getID(), film);
        return film;
    }

    @PutMapping
    public Object updateFilm(@RequestBody @Valid Film filmObject) {
        LOG.setLevel(Level.INFO);

        if (filmObject == null) {
            throw new NullPointerException("Не корректная инициализация объекта типа \"Film\"");
        }

        LOG.debug("Получено ID: " + filmObject.getID());
        LOG.debug("Получено название: " + filmObject.getName().trim());
        LOG.debug("Получено описание: " + filmObject.getDescription().trim());
        LOG.debug("Получена дата: " + filmObject.getReleaseDate());
        LOG.debug("Получена продолжительность: " + filmObject.getDuration());

        Film oldFilm = filmMap.get(filmObject.getID());

        if (oldFilm == null) {
            throw new NullPointerException("В коллекции нет данного фильма");
        }

        if (oldFilm.getID() != null && filmMap.containsKey(filmObject.getID())) {

            boolean nameFilmExists = filmMap.values()
                    .stream()
                    .map(f -> f.getName().trim())
                    .anyMatch(f -> f.equalsIgnoreCase(filmObject.getName().trim()));


            if (nameFilmExists) {
                throw new DuplicatedException("В коллекции фильмов уже есть кино с таким названием. Измените название!");
            }

            oldFilm.setName(film.validationName(filmObject.getName() != null ? filmObject.getName() :
                    oldFilm.getName()));

            oldFilm.setDescription(film.validationDescription(filmObject.getDescription() != null ?
                    filmObject.getDescription() : oldFilm.getDescription()));

            oldFilm.setReleaseDate(film.validationReleaseDate(filmObject.getReleaseDate() != null ?
                    filmObject.getReleaseDate() : oldFilm.getReleaseDate()));

            oldFilm.setDuration(film.validationDuration(filmObject.getDuration() != null ?
                    filmObject.getDuration() : oldFilm.getDuration()));
        }

        return oldFilm;
    }

    @GetMapping
    public Collection<Film> getFilms() {

        if (filmMap.isEmpty()) {
            throw new NullPointerException("В коллекцию фильмов еще не добавлен не один фильм");
        }

        return filmMap.values();
    }

    @DeleteMapping
    public void deleteAllFilms() {

        if (filmMap.isEmpty()) {
            throw new NullPointerException("Коллекция фильмов уже пустая");
        }

        filmMap.clear();
    }
}
