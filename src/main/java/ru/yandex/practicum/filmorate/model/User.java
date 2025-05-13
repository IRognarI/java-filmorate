package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Data
@Builder
public class User extends Film {
    private final DateTimeFormatter FORMATTER = getFORMATTER();

    private long ID;
    @NonNull
    private String email;
    @NonNull
    private String login;
    private String name;
    @Builder.Default
    private LocalDate birthday = LocalDate.now();

    private LocalDate validationBirthday(String birthdayVal) {

        if (birthdayVal == null || birthdayVal.isEmpty()) {
            throw new ValidationException("Дата рождения указана не корректно");
        }

        try {
            LocalDate actualBirthday = LocalDate.parse(birthdayVal.trim(), FORMATTER);

            if (actualBirthday.isAfter(birthday)) {
                throw new ValidationException("Дата рождения не может быть в будущем");

            } else {
                return birthday = actualBirthday;
            }
        } catch (DateTimeParseException e) {
            throw new ValidationException("Не корректный формат даты рождения. Верный формат даты: dd.MM.yyyy");

        } catch (Exception e) {
            throw new ValidationException(e.getMessage());
        }
    }
    /*Класс Film предварительно закончен;
    * В классе User написана валидация даты рождения. Осталось дописать валидацию по остальным полям.*/
}
