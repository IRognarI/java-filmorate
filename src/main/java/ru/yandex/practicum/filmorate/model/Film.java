package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.rating.Rating;

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
    @Getter(AccessLevel.PRIVATE)
    private final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @JsonIgnore
    @Getter
    private final Set<Long> usersWhoLikedIt = new HashSet<>();

    private Long id;

    @NotNull(message = "Укажите название фильма")
    @NotEmpty(message = "Укажите название фильма")
    private String name;

    @NotNull(message = "Заполните описание фильма")
    private String description;

    @NotNull(message = "Укажите дату релиза фильма")
    private LocalDate releaseDate;

    @NotNull(message = "Укажите продолжительность фильма")
    private Integer duration;

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

    public long getLikes() {
        return usersWhoLikedIt.size();
    }
}
