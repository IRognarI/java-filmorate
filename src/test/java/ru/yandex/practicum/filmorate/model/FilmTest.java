package ru.yandex.practicum.filmorate.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

class FilmTest {
    private Film film;

    @BeforeEach
    public void addFilmClass() {
        film = new Film();
    }

    @Test
    public void checkNameForEmptiness() {
        Assertions.assertThrows(NullPointerException.class, () -> film.validationName(null));
        Assertions.assertNull(film.getName());
    }

    @Test
    public void checkingCorrectnessOfName() {
        String name = "Акула";

        film.setName(film.validationName(name));
        Assertions.assertEquals(name, film.getName());
    }

    @Test
    public void checkingExceededLengthOfDescription() {
        String description = "hello";
        int lengthDescription = description.length();

        String checkDescription = description.repeat(film.getMAX_LENGTH_DESCRIPTION() + lengthDescription);

        Assertions.assertNull(film.getDescription());

        Assertions.assertThrows(ValidationException.class, () -> film.validationDescription(checkDescription));
        Assertions.assertThrows(NullPointerException.class, () -> film.validationDescription(null));
    }

    @Test
    public void checkingCorrectDescription() {

        String description = "hello";

        film.setDescription(film.validationDescription(description));

        Assertions.assertNotNull(film.getDescription());
        Assertions.assertEquals(description, film.getDescription());
    }

    @Test
    public void checkingReleaseOfMovieBeforeSetDate() {
        Assertions.assertThrows(ValidationException.class, () -> film.validationReleaseDate("10.05.1894"));
        Assertions.assertNull(film.getReleaseDate());
    }

    @Test
    public void checkingIncorrectFormatOfMovieReleaseDate() {
        Assertions.assertThrows(ValidationException.class, () -> film.validationReleaseDate("10 мая 2007"));
        Assertions.assertNull(film.getReleaseDate());
    }

    @Test
    public void checkingCorrectReleaseDate() {
        String releaseDate = "12.10.2002";

        try {
            LocalDate localDate = LocalDate.parse(releaseDate, film.getFORMAT());

            film.setReleaseDate(film.validationReleaseDate(releaseDate));
            Assertions.assertNotNull(film.getReleaseDate());

            String expectedRelease = String.valueOf(localDate);
            String actualRelease = String.valueOf(film.getReleaseDate());

            Assertions.assertEquals(expectedRelease, actualRelease);
        } catch (DateTimeParseException e) {
            System.out.println("Ошибка преобразования объекта LocalDate из строки");
        }
    }

    @Test
    public void checkNegativeDurationOfMovie() {
        Assertions.assertThrows(ValidationException.class, () -> film.validationDuration(0L));
        Assertions.assertThrows(ValidationException.class, () -> film.validationDuration(-10L));
        Assertions.assertNull(film.getDuration());
    }

    @Test
    public void checkingCorrectDurationOfMovie() {
        long durationFilm = 125L;

        Duration duration = Duration.ofMinutes(durationFilm);
        film.setDuration(film.validationDuration(durationFilm));

        String expectedDuration = String.valueOf(duration);
        String actualDuration = String.valueOf(film.getDuration());

        Assertions.assertEquals(expectedDuration, actualDuration);
    }
}
