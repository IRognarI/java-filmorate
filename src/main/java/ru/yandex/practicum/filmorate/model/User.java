package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class User {
    private Long id;
    @Email(message = "Не корректный формат email адреса")
    private String email;
    private String login;
    private String name;

    //@JsonFormat(pattern = "dd.MM.yyyy")
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

    @Deprecated(forRemoval = true)
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
            throw new ValidationException(
                            "Email адрес не должен содержать пробелы и в адресе должен быть символ: \"@\".\nПример:" +
                                    " some_address@gmail.com");
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

        return userLogin.trim();
    }

    public String validationName(String userName) throws NullPointerException {

        if (userName == null || userName.isEmpty()) {

            if (login != null && !login.isEmpty()) {
                return login;

            } else {
                throw new NullPointerException("Имя не обязательно для заполнения. Но поле \"login\" - обязательно");
            }

        } else {
            return userName.trim();
        }
    }
}
