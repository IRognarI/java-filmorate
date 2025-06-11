package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.time.LocalDate;
import java.util.*;

@Data
@NoArgsConstructor
public class User {
    private Long id;

    @JsonIgnore
    private final Set<Long> friends = new HashSet<>();

    @Email(message = "Не корректный формат email адреса")
    private String email;

    @NotNull(message = "Укажите логин")
    @NotEmpty(message = "Логин не может быть пустым")
    private String login;
    private String name;

    @NotNull(message = "Укажите дату рождения")
    private LocalDate birthday;

    public LocalDate validationBirthday(LocalDate birthdayVal) throws ValidationException {

        if (birthdayVal.isAfter(LocalDate.now())) {
            throw new ValidationException("Дата рождения не может быть в будущем времени");
        }

        if (birthdayVal.isEqual(LocalDate.now())) {
            throw new ValidationException("Вы еще слишком молоды для регистрации ;)");
        }

        return birthdayVal;
    }

    public String validationLogin(String userLogin) throws ValidationException {

        int valueIsBlank = userLogin.trim().indexOf(" ");

        if (valueIsBlank != -1) throw new ValidationException("Логин не может содержать пробелы");

        return userLogin.trim();
    }

    public String validationName(String userName) throws ValidationException {

        if (userName == null || userName.isEmpty()) {

            if (login != null) {
                return login;

            } else {
                throw new ValidationException("Имя не обязательно для заполнения. Но поле \"login\" - обязательно");
            }

        } else {
            return userName.trim();
        }
    }
}
