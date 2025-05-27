package ru.yandex.practicum.filmorate.storage.interfaces;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

public interface FilmStorage {

    Film addFilm(Film filmObject);

    Film updateFilm(Film filmObject);

    Collection<Film> getFilms();

    void deleteAllFilms();
}
