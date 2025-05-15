package ru.yandex.practicum.filmorate.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.time.LocalDate;

class FilmTest extends Film {
    private Film film;


    @BeforeEach
    public void createDefaultFilm() {
        film = new Film();
    }

    @Test
    public void checkNameForEmptiness() {

        Assertions.assertThrows(ValidationException.class, () -> film.validationName(null));
        Assertions.assertThrows(ValidationException.class, () -> film.validationName(""));
    }

    @Test
    public void checkingCorrectnessOfName() {

        film.setName("Shark");
        Assertions.assertNotNull(film.getName());
        Assertions.assertEquals("Shark", film.getName());
    }

    @Test
    public void checkingExceededLengthOfDescription() {
        String description = "hello";
        int lengthDescription = description.length();

        String longerThanMaximumLength = description.repeat(super.MAX_LENGTH_DESCRIPTION + lengthDescription);

        Assertions.assertThrows(ValidationException.class, () -> film.validationDescription(longerThanMaximumLength));
    }

    @Test
    public void checkingCorrectDescription() {
        film.setDescription("Some description");

        Assertions.assertNotNull(film.getDescription());
        Assertions.assertTrue(film.getDescription().length() <= super.MAX_LENGTH_DESCRIPTION);
    }

    @Test
    public void checkingReleaseOfMovieBeforeSetDate() {
        LocalDate releaseDate = LocalDate.of(1894, 05, 10);

        film.setReleaseDate(releaseDate);

        Assertions.assertThrows(ValidationException.class, () -> film.validationReleaseDate(film.getReleaseDate()));
        Assertions.assertTrue(film.getReleaseDate().isBefore(super.MIN_RELEASE_DATE));
    }

    @Test
    public void checkingCorrectReleaseDate() {

        film.setReleaseDate(super.MIN_RELEASE_DATE.plusYears(2));
        Assertions.assertTrue(film.getReleaseDate().isAfter(super.MIN_RELEASE_DATE));
    }

    @Test
    public void checkNegativeDurationOfMovie() {
        Assertions.assertThrows(ValidationException.class, () -> film.validationDuration(0));
        Assertions.assertThrows(ValidationException.class, () -> film.validationDuration(-10));
    }
}
