package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"login", "email"})
public class User {

    @Email(message = "Не корректный формат email адреса")
    private String email;
    private String login;
    private String name;

    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate birthday = LocalDate.now();

    public LocalDate validationBirthday(LocalDate birthdayVal) throws NullPointerException, ValidationException {

        if (birthdayVal == null) {
            throw new NullPointerException("Дата рождения указана не корректно");
        }

        if (birthdayVal.isAfter(birthday)) {
            throw new ValidationException("Дата рождения не может быть в будущем времени");
        }

        if (birthdayVal.isEqual(birthday)) {
            throw new ValidationException("Вы еще слишком молоды для регистрации ;)");
        }

        return birthdayVal;
    }

    @Deprecated
    public String validationEmail(String mail) throws NullPointerException, ValidationException {

        if (mail == null || mail.isEmpty()) throw new NullPointerException("Укажите email адрес");

        boolean correctedEmail = false;

        int valueIsBlank = mail.trim().indexOf(" ");

        if (valueIsBlank != -1) throw new ValidationException("Email адрес не может содержать пробелы");

        char[] valueChars = mail.trim().toCharArray();

        for (char ch : valueChars) {

            if (ch == '@') {
                correctedEmail = true;
                break;
            }
        }

        if (!correctedEmail) {
            throw new ValidationException
                    (
                            "Email адрес не должен содержать пробелы и в адресе должен быть символ: \"@\".\nПример:" +
                                    " some_address@gmail.com"
                    );
        } else {
            return mail.trim();
        }
    }

    public String validationLogin(String userLogin) throws NullPointerException, ValidationException {

        if (userLogin == null || userLogin.isEmpty()) {
            throw new NullPointerException("Логин не может быть пустым");
        }

        int valueIsBlank = userLogin.trim().indexOf(" ");

        if (valueIsBlank != -1) throw new ValidationException("Логин не может содержать пробелы");

        String loginToLowerCase = userLogin.trim().toLowerCase();

        return loginToLowerCase.substring(0, 1).toUpperCase().concat(loginToLowerCase.substring(1));
    }

    public String validationName(String userName) throws NullPointerException {

        if (userName == null || userName.isEmpty()) {

            if (login != null && !login.isEmpty()) {
                return getLogin();

            } else {
                throw new NullPointerException("Имя не обязательно для заполнения. Но поле \"login\" - обязательно");
            }
        }

        String nameToLowerCase = userName.trim().toLowerCase();

        return nameToLowerCase.substring(0, 1).toUpperCase().concat(nameToLowerCase.substring(1));
    }
}
