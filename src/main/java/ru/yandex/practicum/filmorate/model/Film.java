package ru.yandex.practicum.filmorate.model;

import ch.qos.logback.classic.Logger;
import lombok.Builder;
import lombok.Data;
import org.slf4j.LoggerFactory;
import ru.yandex.practicum.filmorate.FilmorateApplication;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Data
@Builder
public class Film {
    private final Logger log = (Logger) LoggerFactory.getLogger(FilmorateApplication.class);

    @Builder.Default
    private final int MAX_LENGTH_DESCRIPTION = 200;
    @Builder.Default
    private final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    @Builder.Default
    private final LocalDate MIN_RELEASE_DATE = LocalDate.of(1895, 12, 28);

    private long ID;
    private String name;
    private String description;
    private LocalDate releaseDate;
    @Builder.Default
    private Duration duration = Duration.ZERO;

    private String validationName(String nameFilms) throws ValidationException {

        if (nameFilms == null || nameFilms.isEmpty()) {
            throw new ValidationException("У фильма должно быть название!");
        }

        String lowerCaseName = nameFilms.toLowerCase().trim();
        String finalFormatName = lowerCaseName.substring(0, 1).toUpperCase().concat(lowerCaseName.substring(1));


        return name = finalFormatName;
    }

    private String validationDescription(String filmDescription) throws ValidationException {

        if (filmDescription == null) {
            throw new ValidationException("Не корректное описание фильма");
        }

        if (filmDescription.length() > MAX_LENGTH_DESCRIPTION) {
            throw new ValidationException("Максимальная длина описания — " + MAX_LENGTH_DESCRIPTION + " символов");
        }

        return description = filmDescription.trim();
    }

    private LocalDate validationReleaseDate(String release) {

        if (release == null || release.isEmpty()) {
            throw new ValidationException("Укажите корректную дату релиза фильма");
        }

        try {
            LocalDate validateReleaseDate = LocalDate.parse(release.trim(), FORMAT);

            boolean actualReleaseDate = validateReleaseDate.isAfter(MIN_RELEASE_DATE);

            if (!actualReleaseDate) {
                throw new ValidationException("Дата релиза должна быть не раньше: " + MIN_RELEASE_DATE.format(FORMAT));
            } else {
                return releaseDate = validateReleaseDate;
            }
        } catch (DateTimeParseException e) {
            throw new ValidationException("Дата релиза указана не корректно. Корректный формат даты: dd.MM.yyyy");
        } catch (Exception e) {
            throw new ValidationException("Ошибка валидации: " + e.getMessage());
        }
    }

    private Duration validationDuration(Long filmDuration) throws NullPointerException, ValidationException {

        if (filmDuration == null) {
            throw new NullPointerException("Укажите корректную продолжительность фильма");
        }

        if (!(filmDuration instanceof Number)) {
            throw new ValidationException("Продолжительность фильма нужно указать в минутах, в виде целого числа");
        }

        if (filmDuration < 1) {
            throw new ValidationException("Продолжительность фильма не может быть: " + filmDuration);
        }

        Duration valideteDuration = Duration.ofMinutes(filmDuration);

        return duration = valideteDuration;
    }
}
