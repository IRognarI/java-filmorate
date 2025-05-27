package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.interfaces.FilmService;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.util.*;

@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {
    private final InMemoryFilmStorage inMemoryFilmStorage;

    @Override
    public Film addFilm(Film filmObject) {
        return inMemoryFilmStorage.addFilm(filmObject);
    }

    @Override
    public Film updateFilm(Film filmObject) {
        return inMemoryFilmStorage.updateFilm(filmObject);
    }

    @Override
    public Collection<Film> getFilms() {
        return inMemoryFilmStorage.getFilms();
    }

    @Override
    public void deleteAllFilms() {
        inMemoryFilmStorage.deleteAllFilms();
    }

    @Override
    public void addLike(Long filmId, Long userId) {

    }

    @Override
    public void removeLike(Long filmId, Long userId) {

    }

    @Override
    public Collection<Film> topOfBestFilms(Integer count) {
        return List.of();
    }
}
