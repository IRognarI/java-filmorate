package ru.yandex.practicum.filmorate.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.time.LocalDate;

class UserTest {
    User user;

    @BeforeEach
    public void addUserClass() {
        user = new User();
    }

    @Test
    public void checkMailWithoutSpecialCharacter() {
        String inCorrectEmail = "someAddressName.com";

        Assertions.assertThrows(ValidationException.class, () -> user.validationEmail(inCorrectEmail));
        Assertions.assertNull(user.getEmail());

    }

    @Test
    public void checkMailForEmptiness() {
        String inCorrectEmail = "";

        Assertions.assertThrows(NullPointerException.class, () -> user.validationEmail(inCorrectEmail));
        Assertions.assertNull(user.getEmail());
    }

    @Test
    public void checkMailForEmptinessWithSpecialCharacter() {
        String inCorrectEmail = "someAddressName @gmail.com";

        Assertions.assertThrows(ValidationException.class, () -> user.validationEmail(inCorrectEmail));
        Assertions.assertNull(user.getEmail());
    }

    @Test
    public void checkingCorrectEmail() {
        String correctEmail = "someAddressName@gmail.com";

        user.validationEmail(correctEmail);
        Assertions.assertEquals(correctEmail, user.getEmail());
    }

    @Test
    public void checkLoginForEmptinessAndWithBlank() {
        String loginEmpty = "";
        String loginWithBlank = "Ale ks";

        Assertions.assertThrows(NullPointerException.class, () -> user.validationLogin(loginEmpty));
        Assertions.assertThrows(NullPointerException.class, () -> user.validationLogin(null));
        Assertions.assertThrows(ValidationException.class, () -> user.validationLogin(loginWithBlank));

        Assertions.assertNull(user.getEmail());
    }

    @Test
    public void checkLoginForCorrectness() {
        String correctedLogin = "Aleks";

        user.validationLogin(correctedLogin);
        Assertions.assertEquals(correctedLogin, user.getLogin());
    }

    /*В данном методе проверяется имя на пустоту. Если оно пустое, то в качестве имени возвращается логин.
     * При условии, что он не тоже не пустой и не null*/

    @Test
    public void checkingNameForEmptiness() {
        String login = "Aleks";
        user.setLogin(login);

        user.validationName(null);
        Assertions.assertEquals(login, user.getName(), "Поле name остается null");
    }

    /*В данном методе логин будет пуст(null), поэтому должно быть выброшено исключение: NullPointerException*/

    @Test
    public void checkingNameAndLoginForEmptiness() {
        Assertions.assertThrows(NullPointerException.class, () -> user.validationName(""));
        Assertions.assertNull(user.getName());
        Assertions.assertNull(user.getLogin());
    }

    @Test
    public void checkCorrectnessOfSetName() {
        String name = "Achilles";

        user.validationName(name);
        Assertions.assertEquals(name, user.getName());
    }

    @Test
    public void checkDateOfBirthInFutureTense() {
        String birthDay = "20.05.2025";

        Assertions.assertThrows(ValidationException.class, () -> user.validationBirthday(birthDay));
        Assertions.assertEquals(user.getBirthday(), LocalDate.now());
    }

    @Test
    public void checkDateOfBirthByToday() {
        String birthDay = "14.05.2025";

        Assertions.assertThrows(ValidationException.class, () -> user.validationBirthday(birthDay));
        Assertions.assertEquals(user.getBirthday(), LocalDate.now());
    }

    @Test
    public void checkingCorrectDateOfBirth() {
        String birthDay = "31.07.1997";

        LocalDate actualBirthDay = LocalDate.parse(birthDay, user.getFORMATTER());
        user.validationBirthday(birthDay);

        Assertions.assertEquals(actualBirthDay, user.getBirthday());
    }

    @Test
    public void checkingCorrectnessOfDateOfBirthFormat() {
        String inCorrectFormatBirthday = "14 мая 2013";
        String emptyBirthday = "";

        Assertions.assertThrows(ValidationException.class, () -> user.validationBirthday(inCorrectFormatBirthday));
        Assertions.assertThrows(NullPointerException.class, () -> user.validationBirthday(emptyBirthday));
        Assertions.assertThrows(NullPointerException.class, () -> user.validationBirthday(null));
        Assertions.assertEquals(LocalDate.now(), user.getBirthday());
    }
}