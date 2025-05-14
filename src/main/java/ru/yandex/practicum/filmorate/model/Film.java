package ru.yandex.practicum.filmorate.model;

import ch.qos.logback.classic.Logger;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.LoggerFactory;
import ru.yandex.practicum.filmorate.FilmorateApplication;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@NoArgsConstructor
@Data
public class Film {
    private final Logger LOG = (Logger) LoggerFactory.getLogger(FilmorateApplication.class);

    private final int MAX_LENGTH_DESCRIPTION = 200;
    protected final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private final LocalDate MIN_RELEASE_DATE = LocalDate.of(1895, 12, 28);

    private long ID;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Duration duration;

    public String validationName(String nameFilms) throws NullPointerException, ValidationException {

        if (nameFilms == null || nameFilms.isEmpty()) {
            throw new NullPointerException("У фильма должно быть название!");
        }

        String lowerCaseName = nameFilms.toLowerCase().trim();
        String finalFormatName = lowerCaseName.substring(0, 1).toUpperCase().concat(lowerCaseName.substring(1));


        return finalFormatName;
    }

    public String validationDescription(String filmDescription) throws NullPointerException, ValidationException {

        if (filmDescription == null) {
            throw new NullPointerException("Не корректное описание фильма");
        }

        if (filmDescription.length() > MAX_LENGTH_DESCRIPTION) {
            throw new ValidationException("Максимальная длина описания — " + MAX_LENGTH_DESCRIPTION + " символов");
        }

        return filmDescription.trim();
    }

    public LocalDate validationReleaseDate(String release) throws NullPointerException {

        if (release == null || release.isEmpty()) {
            throw new NullPointerException("Укажите корректную дату релиза фильма");
        }

        try {
            LocalDate validateReleaseDate = LocalDate.parse(release.trim(), FORMAT);

            boolean actualReleaseDate = validateReleaseDate.isAfter(MIN_RELEASE_DATE);

            if (!actualReleaseDate) {
                throw new ValidationException("Дата релиза должна быть не раньше: " + MIN_RELEASE_DATE.format(FORMAT));
            } else {
                return validateReleaseDate;
            }
        } catch (DateTimeParseException e) {
            throw new ValidationException("Формат даты релиза указан не корректно. Корректный формат даты: dd.MM.yyyy");
        } catch (Exception e) {
            throw new ValidationException("Ошибка валидации: " + e.getMessage());
        }
    }

    public Duration validationDuration(Long filmDuration) throws NullPointerException, ValidationException {

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

        return valideteDuration;
    }
}
