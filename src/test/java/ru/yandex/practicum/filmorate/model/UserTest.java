package ru.yandex.practicum.filmorate.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.time.LocalDate;

class UserTest {
    private User user;

    @BeforeEach
    public void addUserClass() {
        user = new User();
    }

    @Test
    public void checkLoginForEmptinessAndWithBlank() {
        String loginEmpty = "";
        String loginWithBlank = "Ale ks";

        Assertions.assertThrows(ValidationException.class, () -> user.validationLogin(loginEmpty));
        Assertions.assertThrows(ValidationException.class, () -> user.validationLogin(null));
        Assertions.assertThrows(ValidationException.class, () -> user.validationLogin(loginWithBlank));

        Assertions.assertNull(user.getEmail());
    }

    @Test
    public void checkLoginForCorrectness() {
        String correctedLogin = "aleks";

        user.setLogin(user.validationLogin(correctedLogin));
        Assertions.assertEquals(correctedLogin, user.getLogin());
    }

    @Test
    public void checkingNameForEmptiness() {
        String login = "";
        user.setLogin("Алекс");

        user.setName(user.validationName(login));
        Assertions.assertEquals(user.getLogin(), user.getName(), "Поле name остается null");
    }

    @Test
    public void checkingNameAndLoginForEmptiness() {
        Assertions.assertThrows(ValidationException.class, () -> user.validationName(""));
        Assertions.assertNull(user.getName());
        Assertions.assertNull(user.getLogin());
    }

    @Test
    public void checkCorrectnessOfSetName() {
        String name = "Achilles";

        user.setName(user.validationName(name));
        Assertions.assertEquals(name, user.getName());
    }

    @Test
    public void checkDateOfBirthInFutureTense() {
        LocalDate date = LocalDate.now().plusYears(1);

        Assertions.assertThrows(ValidationException.class, () -> user.setBirthday(user.validationBirthday(date)));
    }

    @Test
    public void checkDateOfBirthByToday() {
        LocalDate date = LocalDate.now();

        Assertions.assertThrows(ValidationException.class, () -> user.setBirthday(user.validationBirthday(date)));
    }

    @Test
    public void checkingCorrectDateOfBirth() {
        LocalDate date = LocalDate.of(1998, 07, 29);

        user.setBirthday(user.validationBirthday(date));
        Assertions.assertEquals(date, user.getBirthday());
    }
}
