package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
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
    public Long removeLike(Long filmId, Long userId) throws ValidationException, NotFoundException {

        if (filmId == null || userId == null) {
            throw new ValidationException("Id фильма и id пользователя должны быть указаны");
        }

        if (filmId <= 0 || userId <= 0) {
            throw new ValidationException("id фильма и id пользователя должны быть больше 0");
        }

        Film film = filmStorage.getFilmMap().get(filmId);

        if (film == null) {
            throw new NotFoundException("Фильм с id [" + filmId + "] - не найден");
        }

        User user = userStorage.getUserMap().get(userId);

        if (user == null) {
            throw new NotFoundException("Пользователь с id [" + userId + "] - не найден");
        }

        if (!film.getUsersWhoLikedIt().contains(user.getId())) {
            throw new NotFoundException("Пользователь с id [" + user.getId() + "] - не поставил лайк");
        }

        film.getUsersWhoLikedIt().remove(user.getId());

        return film.getLikes();
    }

    @Override
    public Long addLike(Long filmId, Long userId) throws ValidationException, NotFoundException {

        if (filmId == null || userId == null) {
            throw new ValidationException("Id фильма и id пользователя должны быть указаны");
        }

        if (filmId <= 0 || userId <= 0) {
            throw new ValidationException("id фильма и id пользователя должны быть больше 0");
        }

        Film film = filmStorage.getFilmMap().get(filmId);

        if (film == null) {
            throw new NotFoundException("Фильм с id [" + filmId + "] - не найден");
        }

        User user = userStorage.getUserMap().get(userId);

        if (user == null) {
            throw new NotFoundException("Пользователь с id [" + userId + "] - не найден");
        }

        film.getUsersWhoLikedIt().add(user.getId());

        return film.getLikes();
    }

    @Override
    public Collection<Film> topOfBestFilms(Integer count) {

        if (count < 0) {
            throw new ValidationException("Ограничение - должно быть положительным числом, а у вас [" + count + "]");
        }

        return filmStorage.getFilmMap().values()
                .stream()
                .sorted(Comparator.comparing(Film::getLikes).reversed())
                .limit(count)
                .toList();
    }
}
