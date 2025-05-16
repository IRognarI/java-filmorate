package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.DuplicatedException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private Film film;
    private final Map<Long, Film> filmMap = new HashMap<>();

    private Long nextID() {
        long id = filmMap.keySet()
                .stream()
                .mapToLong(i -> i)
                .max()
                .orElse(0);
        return ++id;
    }

    @PostMapping
    public Object addFilm(@RequestBody @Valid Film filmObject) {

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

        log.debug("Получено ID: {}", filmObject.getID());
        log.debug("Получено название: {}", filmObject.getName().trim());
        log.debug("Получено описание: {}", filmObject.getDescription().trim());
        log.debug("Получена дата: ", filmObject.getReleaseDate());
        log.debug("Получена продолжительность: {}", filmObject.getDuration());

        film = new Film();

        film.setID(nextID());
        film.setName(film.validationName(filmObject.getName()));
        film.setDescription(film.validationDescription(filmObject.getDescription()));
        film.setReleaseDate(film.validationReleaseDate(filmObject.getReleaseDate()));
        film.setDuration(film.validationDuration(filmObject.getDuration()));

        filmMap.put(film.getID(), film);
        return film;
    }

    @PutMapping
    public Object updateFilm(@RequestBody @Valid Film filmObject) {

        if (filmObject == null) {
            throw new NullPointerException("Не корректная инициализация объекта типа \"Film\"");
        }

        log.debug("Получено ID: " + filmObject.getID());
        log.debug("Получено название: " + filmObject.getName().trim());
        log.debug("Получено описание: " + filmObject.getDescription().trim());
        log.debug("Получена дата: " + filmObject.getReleaseDate());
        log.debug("Получена продолжительность: " + filmObject.getDuration());

        Film oldFilm = filmMap.get(filmObject.getID());

        if (oldFilm == null) {
            //throw new NullPointerException("В коллекции нет данного фильма");
            addFilm(filmObject);
        }

        if (oldFilm.getID() != null && filmMap.containsKey(filmObject.getID())) {

            boolean nameFilmExists = filmMap.values()
                    .stream()
                    .map(f -> f.getName().trim())
                    .anyMatch(f -> f.equalsIgnoreCase(filmObject.getName().trim()));


            if (nameFilmExists) {
                throw new DuplicatedException("В коллекции фильмов уже есть кино с таким названием. Измените название!");
            }

            oldFilm.setName(film.validationName(filmObject.getName()));

            oldFilm.setDescription(film.validationDescription(!filmObject.getDescription().

                    equalsIgnoreCase(oldFilm.getDescription()) ? filmObject.getDescription() : oldFilm.getDescription()));

            oldFilm.setReleaseDate(film.validationReleaseDate(!filmObject.getReleaseDate().

                    isEqual(oldFilm.getReleaseDate()) ? filmObject.getReleaseDate() : oldFilm.getReleaseDate()));

            oldFilm.setDuration(film.validationDuration(filmObject.getDuration() != oldFilm.getDuration() ?

                    filmObject.getDuration() : oldFilm.getDuration()));
        }

        return oldFilm;
    }

    @GetMapping
    public Collection<Film> getFilms() {

        /*if (filmMap.isEmpty()) {
            throw new NullPointerException("В коллекцию фильмов еще не добавлен не один фильм");
        }*/

        return filmMap.values();
    }

    @DeleteMapping
    public void deleteAllFilms() {

        /*if (filmMap.isEmpty()) {
            throw new NullPointerException("Коллекция фильмов уже пустая");
        }*/

        filmMap.clear();
    }
}
