package ru.yandex.practicum.filmorate.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.time.LocalDate;
import java.util.*;

class FilmTest extends Film {
    private Film film;


    @BeforeEach
    public void createDefaultFilm() {
        film = new Film();
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

        String longerThanMaximumLength = description.repeat(super.maxLengthDescription + lengthDescription);

        Assertions.assertThrows(ValidationException.class, () -> film.validationDescription(longerThanMaximumLength));
    }

    @Test
    public void checkingCorrectDescription() {
        film.setDescription("Some description");

        Assertions.assertNotNull(film.getDescription());
        Assertions.assertTrue(film.getDescription().length() <= super.maxLengthDescription);
    }

    @Test
    public void checkingReleaseOfMovieBeforeSetDate() {
        LocalDate releaseDate = LocalDate.of(1894, 05, 10);

        film.setReleaseDate(releaseDate);

        Assertions.assertThrows(ValidationException.class, () -> film.validationReleaseDate(film.getReleaseDate()));
        Assertions.assertTrue(film.getReleaseDate().isBefore(super.minReleaseDate));
    }

    @Test
    public void checkingCorrectReleaseDate() {

        film.setReleaseDate(super.minReleaseDate.plusYears(2));
        Assertions.assertTrue(film.getReleaseDate().isAfter(super.minReleaseDate));
    }

    @Test
    public void checkNegativeDurationOfMovie() {
        Assertions.assertThrows(ValidationException.class, () -> film.validationDuration(0));
        Assertions.assertThrows(ValidationException.class, () -> film.validationDuration(-10));
    }

    @Test
    public void CheckingPresenceOfRatingIndatabase() {

        film.setRating(film.validationRating("r"));
        Assertions.assertEquals("R", film.getRating());
    }

    @Test
    public void settingThePG_13rating() {

        film.setRating(film.validationRating("pg-13"));
        Assertions.assertEquals("PG_13", film.getRating());
    }

    @Test
    public void settingNonExistentRating() {

        Assertions.assertThrows(NotFoundException.class, () -> film.validationRating("y"));
    }

    @Test
    public void checkingPresenceOfGenres() {

        String[] filmGenres = {"Боевик", "Драма", "Документальный"};

        film.getGenresFilm().addAll(List.of(film.validationGenre(filmGenres)));

        Assertions.assertEquals(3, film.getGenresFilm().toArray().length);
    }

    @Test
    public void checkingLackOfGenres() {

        String[] filmGenres = {};
        Assertions.assertThrows(ValidationException.class, () -> film.validationGenre(filmGenres));
    }

    @Test
    public void addingOneGenre() {
        String[] filmGenres = {"Боевик"};

        film.getGenresFilm().addAll(List.of(film.validationGenre(filmGenres)));

        Assertions.assertEquals(1, film.getGenresFilm().toArray().length);
    }
}
