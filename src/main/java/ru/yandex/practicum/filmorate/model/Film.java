package ru.yandex.practicum.filmorate.model;

<<<<<<< HEAD
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.rating.Rating;
=======
import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.annotation.ReleaseDate;
>>>>>>> b13c2dd (Произведен рефакторинг кода: 1. Исправлены валидаторы в POJO (заменены на аннотации), также согласно условию ТЗ добавлены новые сущности + реализована работа с БД Н2. Изменена структура дирректорий проекта. Добавлены новые интерфейсы и контроллеры. Добавлены DAO классы (реализующие новые интерфейсы). Приложение проверено через Postman. Все тесты пройдены успешно.)

import java.time.LocalDate;
import java.util.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
@Builder
public class Film {
    private Integer id;
    @NotBlank(message = "Введите название фильма.")
    private String name;
    @NotNull
    @Size(max = 200, message = "Слишком длинное описание.")
    private String description;
    @NotNull
    @ReleaseDate(value = "1895-12-28", message = "Введите дату релиза не ранее 28 декабря 1895 года.")
    private LocalDate releaseDate;
    @Positive(message = "Продолжительность фильма должна быть больше 0.")
    private Integer duration;
<<<<<<< HEAD

    @Setter(AccessLevel.NONE)
    private Long likes = getLiusersWhoLikedItkesSize();

    private Set<String> genresFilm = new TreeSet<>();

    @NotNull(message = "Укажите возрастной рейтинг фильма")
    private String rating;

    public String[] validationGenre(String[] genres) {

        if (genres.length == 0) {
            throw new ValidationException("Укажите хотя бы один жанр фильма");
        }
        return genres;
    }

    public String validationRating(String filmRating) {
        filmRating = filmRating.toUpperCase().trim();

        int targetIndexChar = filmRating.indexOf("-");

        if (targetIndexChar != -1) {
            filmRating = filmRating.replace("-", "_");
        }

        boolean ratingExists = false;
        for (Enum<Rating> r : Rating.values()) {
            String targetRating = String.valueOf(r);

            if (filmRating.equals(targetRating)) {
                ratingExists = true;
                break;
            }
        }

        if (!ratingExists) {
            throw new NotFoundException("Рейтинг: " + filmRating + " - не признан ассоциацией кинокомпаний");
        }
        return filmRating;
    }

    public String validationDescription(String filmDescription) throws ValidationException {

        if (filmDescription.length() > maxLengthDescription) {
            throw new ValidationException("Максимальная длина описания — " + maxLengthDescription + " символов");
        }

        return filmDescription.trim();
    }

    public LocalDate validationReleaseDate(LocalDate release) throws ValidationException {

        if (release.isBefore(minReleaseDate)) {
            throw new ValidationException("Дата релиза не может быть раньше: " + minReleaseDate.format(getFormat()));
        }
        return release;
    }

    public Integer validationDuration(Integer filmDuration) throws ValidationException {

        if (filmDuration < 1) {
            throw new ValidationException("Продолжительность фильма не может быть: " + filmDuration);
        }

        return filmDuration;
    }

    private long getLiusersWhoLikedItkesSize() {
        return usersWhoLikedIt.size();
    }
=======
    @NotNull
    private Mpa mpa;
    private final LinkedHashSet<Genre> genres = new LinkedHashSet<>();
>>>>>>> b13c2dd (Произведен рефакторинг кода: 1. Исправлены валидаторы в POJO (заменены на аннотации), также согласно условию ТЗ добавлены новые сущности + реализована работа с БД Н2. Изменена структура дирректорий проекта. Добавлены новые интерфейсы и контроллеры. Добавлены DAO классы (реализующие новые интерфейсы). Приложение проверено через Postman. Все тесты пройдены успешно.)
}
