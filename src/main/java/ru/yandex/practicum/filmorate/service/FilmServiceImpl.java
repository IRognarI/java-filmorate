package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.interfaces.FilmService;
import ru.yandex.practicum.filmorate.storage.interfaces.FilmStorage;

import java.util.*;

@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {

    @Autowired
    @Qualifier("InMemoryFilmStorage")
    private final FilmStorage filmStorage;

    @Override
    public void addLike(long filmId, long userId) {

    }

    @Override
    public void removeLike(long filmId, long userId) {

    }

    @Override
    public Collection<Film> topOfBestFilms(int count) {
        return List.of();
    }
}
