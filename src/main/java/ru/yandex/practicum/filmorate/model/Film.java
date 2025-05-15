package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@Data
@EqualsAndHashCode(of = {"ID", "name"})
public class Film {
    @JsonIgnore
    @Getter(AccessLevel.NONE)
    protected final int MAX_LENGTH_DESCRIPTION = 200;

    @JsonIgnore
    @Getter(AccessLevel.NONE)
    protected final LocalDate MIN_RELEASE_DATE = LocalDate.of(1895, 12, 28);

    @JsonIgnore
    @Getter(AccessLevel.NONE)
    private final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private Long ID;
    private String name;
    private String description;

    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate releaseDate;
    private Integer duration;

    public String validationName(String nameFilms) throws ValidationException {

        if (nameFilms == null || nameFilms.trim().isEmpty()) {
            throw new ValidationException("Имя не может быть пустым");
        }

        String normalizeName = nameFilms.trim().substring(0, 1).toUpperCase() +
                nameFilms.trim().substring(1).toLowerCase();

        return normalizeName;
    }

    public String validationDescription(String filmDescription) throws ValidationException {

        if (filmDescription == null) {
            throw new ValidationException("Не корректное описание фильма");
        }

        if (filmDescription.length() > MAX_LENGTH_DESCRIPTION) {
            throw new ValidationException("Максимальная длина описания — " + MAX_LENGTH_DESCRIPTION + " символов");
        }

        return filmDescription.trim();
    }

    public LocalDate validationReleaseDate(LocalDate release) throws ValidationException {

        if (release == null) {
            throw new ValidationException("Укажите корректную дату релиза фильма");
        }

        if (release.isBefore(MIN_RELEASE_DATE)) {
            throw new ValidationException("Дата релиза не может быть раньше: " + MIN_RELEASE_DATE.format(FORMAT));
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
