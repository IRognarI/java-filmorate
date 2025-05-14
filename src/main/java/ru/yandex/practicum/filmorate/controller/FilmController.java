package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

@RestController
@RequestMapping("/films")
public class FilmController {

    private final Film film = new Film();
    private final Map<Long, Film> filmMap = new HashMap<>();

    private Long nextID() {
        long ID = filmMap.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++ID;
    }

    @PostMapping
    public Film addFilm(@RequestBody Film filmObject) {

        if (filmObject == null) {
            throw new ValidationException("Не корректная инициализация объекта типа \"Film\"");
        }

        film.setID(nextID());
        film.setName(film.validationName(filmObject.getName()));
        film.setDescription(film.validationDescription(filmObject.getDescription()));

        String release = filmObject.getReleaseDate().format(film.getFORMAT());
        long duration = filmObject.getDuration().toMinutes();

        film.setReleaseDate(film.validationReleaseDate(release));
        film.setDuration(film.validationDuration(duration));

        if (film == null) {
            throw new ValidationException("Объект не может быть создан! Нужна проверка корректности создания объекта!");
        } else {
            filmMap.put(film.getID(), film);
            return film;
        }
    }
}
