package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.interfaces.FilmService;
import ru.yandex.practicum.filmorate.storage.interfaces.FilmStorage;
import ru.yandex.practicum.filmorate.storage.interfaces.UserStorage;

import java.util.*;

@Service
@RequiredArgsConstructor
@Primary
public class FilmServiceImpl implements FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    @Override
    public Film addFilm(Film filmObject) {
        return filmStorage.addFilm(filmObject);
    }

    @Override
    public Film updateFilm(Film filmObject) {
        return filmStorage.updateFilm(filmObject);
    }

    @Override
    public Collection<Film> getFilms() {
        return filmStorage.getFilms();
    }

    @Override
    public void deleteAllFilms() {
        filmStorage.deleteAllFilms();
    }

    @Override
    public Long removeLike(Long filmId, Long userId) {

        if (filmId == null) throw new ValidationException("Укажите ID фильма");

        if (filmId <= 0) throw new ValidationException("ID фильма не может быть [" + filmId + "]");

        if (userId == null) throw new ValidationException("Укажите ID пользователя");

        if (userId <= 0) throw new ValidationException("ID пользователя не может быть [" + filmId + "]");

        if (!filmStorage.getFilmMap().containsKey(filmId)) {
            throw new NotFoundException("Фильм с ID [" + filmId + "] - не найден");
        }

        if (!userStorage.getUserMap().containsKey(userId)) {
            throw new NotFoundException("Пользователь с ID [" + userId + "] - не найден");
        }

        Film film = filmStorage.getFilmMap().get(filmId);

        boolean likeExists = film.getUsersWhoLikedIt()
                .stream()
                .anyMatch(id -> id.equals(userId));

        if (!likeExists) {
            throw new NotFoundException("Пользователь с ID [" + userId + "] - не успел оценить фильм");
        }

        film.getUsersWhoLikedIt().remove(userId);

        return film.getLikes();
    }

    @Override
    public Long addLike(Long filmId, Long userId) {

        if (filmId == null) throw new ValidationException("Укажите ID фильма");

        if (filmId <= 0) throw new ValidationException("ID фильма не может быть [" + filmId + "]");

        if (userId == null) throw new ValidationException("Укажите ID пользователя");

        if (userId <= 0) throw new ValidationException("ID пользователя не может быть [" + filmId + "]");

        if (!filmStorage.getFilmMap().containsKey(filmId)) {
            throw new NotFoundException("Фильм с ID [" + filmId + "] - не найден");
        }

        if (!userStorage.getUserMap().containsKey(userId)) {
            throw new NotFoundException("Пользователь с ID [" + userId + "] - не найден");
        }

        Film film = filmStorage.getFilmMap().get(filmId);

        film.getUsersWhoLikedIt().add(userId);

        return film.getLikes();
    }

    @Override
    public Collection<Film> topOfBestFilms(Integer count) {

        return filmStorage.getFilmMap().values()
                .stream()
                .sorted(Comparator.comparing(Film::getLikes).reversed())
                .limit(count)
                .toList();
    }
}
