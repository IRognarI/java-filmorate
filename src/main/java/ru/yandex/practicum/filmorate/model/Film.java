package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@NoArgsConstructor
@Data
public class Film {
    @JsonIgnore
    @Getter(AccessLevel.NONE)
    protected final int maxLengthDescription = 200;

    @JsonIgnore
    @Getter(AccessLevel.NONE)
    protected final LocalDate minReleaseDate = LocalDate.of(1895, 12, 28);

    @JsonIgnore
    @Getter(AccessLevel.NONE)
    private final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @JsonIgnore
    @Getter(AccessLevel.NONE)
    private final Set<Long> usersWhoLikedIt = new HashSet<>();

    private Long id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Integer duration;

    public long getLikes() {
        return usersWhoLikedIt.size();
    }

    public String validationName(String nameFilms) throws ValidationException {

        if (nameFilms == null || nameFilms.trim().isEmpty()) {
            throw new ValidationException("Имя не может быть пустым");
        }

        return nameFilms.trim();
    }

    public String validationDescription(String filmDescription) throws ValidationException {

        if (filmDescription == null) {
            throw new ValidationException("Не корректное описание фильма");
        }

        if (filmDescription.length() > maxLengthDescription) {
            throw new ValidationException("Максимальная длина описания — " + maxLengthDescription + " символов");
        }

        return filmDescription.trim();
    }

    public LocalDate validationReleaseDate(LocalDate release) throws ValidationException {

        if (release == null) {
            throw new ValidationException("Укажите корректную дату релиза фильма");
        }

        if (release.isBefore(minReleaseDate)) {
            throw new ValidationException("Дата релиза не может быть раньше: " + minReleaseDate.format(format));
        }
        return release;
    }

    public Integer validationDuration(Integer filmDuration) throws ValidationException {

        if (filmDuration == null) {
            throw new ValidationException("Укажите корректную продолжительность фильма");
        }

        if (filmDuration < 1) {
            throw new ValidationException("Продолжительность фильма не может быть: " + filmDuration);
        }

        return filmDuration;
    }
}
