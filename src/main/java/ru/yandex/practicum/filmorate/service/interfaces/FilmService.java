package ru.yandex.practicum.filmorate.service.interfaces;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

public interface FilmService {

    Film addFilm(Film filmObject);

    Film updateFilm(Film filmObject);

    Collection<Film> getFilms();

    void deleteAllFilms();

    void addLike(Long filmId, Long userId);

    void removeLike(Long filmId, Long userId);

    Collection<Film> topOfBestFilms(Integer count);
}
