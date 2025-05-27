package ru.yandex.practicum.filmorate.service.interfaces;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

public interface FilmService {

    void addLike(long filmId, long userId);

    void removeLike(long filmId, long userId);

    Collection<Film> topOfBestFilms(int count);
}
