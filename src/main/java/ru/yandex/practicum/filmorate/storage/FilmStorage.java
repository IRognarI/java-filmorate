package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

public interface FilmStorage {

    public Film addFilm(Film filmObject);

    public Film updateFilm(Film filmObject);

    public Collection<Film> getFilms();

    public void deleteAllFilms();
}
