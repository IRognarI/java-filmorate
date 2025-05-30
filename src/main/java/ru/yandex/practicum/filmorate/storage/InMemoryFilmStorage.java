package ru.yandex.practicum.filmorate.storage;

import lombok.Getter;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.DuplicatedException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.interfaces.FilmStorage;

import java.util.*;

@Component
@Primary
public class InMemoryFilmStorage implements FilmStorage {

    @Getter
    private final Map<Long, Film> filmMap = new HashMap<>();

    @Override
    public Film addFilm(Film filmObject) throws ValidationException, DuplicatedException {
        if (filmObject == null) {
            throw new ValidationException("Не достаточно данных для добавления фильма");
        }

        boolean nameFilmExists = getFilmMap().values()
                .stream()
                .anyMatch(f -> f.getName().trim().equalsIgnoreCase(filmObject.getName().trim()));

        if (nameFilmExists) {
            throw new DuplicatedException("Кино уже было добавлено");
        }

        Film film = new Film();

        film.setId(nextID());
        film.setName(film.validationName(filmObject.getName()));
        film.setDescription(film.validationDescription(filmObject.getDescription()));
        film.setReleaseDate(film.validationReleaseDate(filmObject.getReleaseDate()));
        film.setDuration(film.validationDuration(filmObject.getDuration()));

        filmMap.put(film.getId(), film);
        return film;
    }

    @Override
    public Film updateFilm(Film filmObject) throws NotFoundException, DuplicatedException {
        if (filmObject == null) {
            addFilm(filmObject);
        }

        if (filmObject.getId() == null) {
            throw new ValidationException("Id фильма должен быть указан");
        }

        Film oldFilm = getFilmMap().get(filmObject.getId());

        if (oldFilm == null) {
            throw new NotFoundException("Фильм с id [" + filmObject.getId() + "] - не найден");
        }

        boolean nameFilmExists = getFilmMap().values()
                .stream()
                .anyMatch(f -> f.getName().trim().equalsIgnoreCase(filmObject.getName().trim()));

        if (nameFilmExists) {
            throw new DuplicatedException("В коллекции фильмов уже есть кино с таким названием. Измените название!");
        }

        if (!oldFilm.getName().equalsIgnoreCase(filmObject.getName())) {
            oldFilm.setName(oldFilm.validationName(filmObject.getName()));
        }

        oldFilm.setDescription(filmObject.getDescription());

        if (!oldFilm.getReleaseDate().isEqual(filmObject.getReleaseDate())) {
            oldFilm.setReleaseDate(oldFilm.validationReleaseDate(filmObject.getReleaseDate()));
        }

        oldFilm.setDuration(filmObject.getDuration());

        return oldFilm;
    }

    @Override
    public Collection<Film> getFilms() {
        return getFilmMap().values();
    }

    @Override
    public void deleteAllFilms() {
        getFilmMap().clear();
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
