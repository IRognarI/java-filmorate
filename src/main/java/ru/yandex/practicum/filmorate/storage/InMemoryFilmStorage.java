package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.DuplicatedException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.interfaces.FilmStorage;

import java.util.*;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Long, Film> filmMap = new HashMap<>();
    private Film film;

    @Override
    public Film addFilm(Film filmObject) {
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

        log.debug("Получено ID: {}", filmObject.getId());
        log.debug("Получено название: {}", filmObject.getName().trim());
        log.debug("Получено описание: {}", filmObject.getDescription().trim());
        log.debug("Получена дата: ", filmObject.getReleaseDate());
        log.debug("Получена продолжительность: {}", filmObject.getDuration());

        film = new Film();

        film.setId(nextID());
        film.setName(film.validationName(filmObject.getName()));
        film.setDescription(film.validationDescription(filmObject.getDescription()));
        film.setReleaseDate(film.validationReleaseDate(filmObject.getReleaseDate()));
        film.setDuration(film.validationDuration(filmObject.getDuration()));

        filmMap.put(film.getId(), film);
        return film;
    }

    @Override
    public Film updateFilm(Film filmObject) {
        if (filmObject == null) {
            throw new NullPointerException("Не корректная инициализация объекта типа \"Film\"");
        }

        log.debug("Получено ID: " + filmObject.getId());
        log.debug("Получено название: " + filmObject.getName().trim());
        log.debug("Получено описание: " + filmObject.getDescription().trim());
        log.debug("Получена дата: " + filmObject.getReleaseDate());
        log.debug("Получена продолжительность: " + filmObject.getDuration());

        Film oldFilm = filmMap.get(filmObject.getId());

        if (oldFilm == null) {
            addFilm(filmObject);
        }

        if (oldFilm.getId() != null && filmMap.containsKey(filmObject.getId())) {

            boolean nameFilmExists = filmMap.values()
                    .stream()
                    .map(f -> f.getName().trim())
                    .anyMatch(f -> f.equalsIgnoreCase(filmObject.getName().trim()));


            if (nameFilmExists) {
                throw new DuplicatedException("В коллекции фильмов уже есть кино с таким названием. Измените название!");
            }

            oldFilm.setName(film.validationName(filmObject.getName()));

            oldFilm.setDescription(film.validationDescription(!filmObject.getDescription()
                    .equalsIgnoreCase(oldFilm.getDescription()) ? filmObject.getDescription() : oldFilm.getDescription()));

            oldFilm.setReleaseDate(film.validationReleaseDate(!filmObject.getReleaseDate()
                    .isEqual(oldFilm.getReleaseDate()) ? filmObject.getReleaseDate() : oldFilm.getReleaseDate()));

            oldFilm.setDuration(film.validationDuration(filmObject.getDuration() != oldFilm.getDuration() ?
                    filmObject.getDuration() : oldFilm.getDuration()));
        }

        return oldFilm;
    }

    @Override
    public Collection<Film> getFilms() {
        return filmMap.values();
    }

    @Override
    public void deleteAllFilms() {
        filmMap.clear();
    }

    private Long nextID() {
        long id = filmMap.keySet()
                .stream()
                .mapToLong(i -> i)
                .max()
                .orElse(0);
        return ++id;
    }
}
