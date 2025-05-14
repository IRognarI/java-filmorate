package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Data
@NoArgsConstructor
public class User extends Film {
    private final DateTimeFormatter FORMATTER = super.FORMAT;

    @NonNull
    private String email;
    @NonNull
    private String login;
    private LocalDate birthday = LocalDate.now();

    public LocalDate validationBirthday(String birthdayVal) throws NullPointerException, ValidationException {

        if (birthdayVal == null || birthdayVal.isEmpty()) {
            throw new NullPointerException("Дата рождения указана не корректно");
        }

        try {
            LocalDate actualBirthday = LocalDate.parse(birthdayVal.trim(), FORMATTER);

            if (actualBirthday.isAfter(birthday)) {
                throw new ValidationException("Дата рождения не может быть в будущем");

            } else if (actualBirthday.isEqual(birthday)) {
                throw new ValidationException("Вы еще слишком молоды для регистрации :)");

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
     * В классе User написана валидация даты рождения. Осталось дописать валидацию по остальным полям.
     * Продолжить, здесь, писать методы валидации...*/

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
            return email = mail.trim();
        }
    }

    public String validationLogin(String userLogin) throws NullPointerException, ValidationException {

        if (userLogin == null || userLogin.isEmpty()) {
            throw new NullPointerException("Логин не может быть пустым");
        }

        int valueIsBlank = userLogin.trim().indexOf(" ");

        if (valueIsBlank != -1) throw new ValidationException("Логин не может содержать пробелы");

        return login = userLogin.trim();
    }

    public String validationName(String userName) throws NullPointerException {

        if (userName == null || userName.isEmpty()) {

            if (login != null && !login.isEmpty()) {
                setName(login);
                return getName();
            } else {
                throw new NullPointerException("Имя не обязательно для заполнения. Но поле \"login\" - обязательно");
            }
        }

        setName(userName.trim());
        return getName();
    }
}
