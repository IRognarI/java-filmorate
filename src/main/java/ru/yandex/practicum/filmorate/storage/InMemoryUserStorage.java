package ru.yandex.practicum.filmorate.storage;

import lombok.Getter;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.DuplicatedException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.interfaces.UserStorage;

import java.util.*;

@Component
@Primary
public class InMemoryUserStorage implements UserStorage {

    @Getter
    private final Map<Long, User> userMap = new HashMap<>();

    @Override
    public User createUser(User userObject) throws ValidationException, DuplicatedException {
        if (userObject == null) {
            throw new ValidationException("Не достаточно данных для регистрации пользователя пользователя");
        }

        boolean logiExists = userMap.values()
                .stream()
                .anyMatch(u -> u.getLogin().trim().equalsIgnoreCase(userObject.getLogin()));

        if (logiExists) {
            throw new DuplicatedException("Логин: " + userObject.getLogin() + " - занят");
        }

        boolean emailExists = userMap.values()
                .stream()
                .anyMatch(u -> u.getEmail().trim().equalsIgnoreCase(userObject.getEmail().trim()));

        if (emailExists) {
            throw new DuplicatedException("Email адрес: " + userObject.getEmail() + " - занят");
        }

        User user = new User();

        user.setId(nextID());
        user.setEmail(userObject.getEmail());
        user.setLogin(user.validationLogin(userObject.getLogin()));
        user.setName(user.validationName(userObject.getName()));
        user.setBirthday(user.validationBirthday(userObject.getBirthday()));

        userMap.put(user.getId(), user);

        return user;
    }

    @Override
    public User updateUser(User userObject) throws DuplicatedException, ValidationException {
        if (userObject == null) {
            return createUser(userObject);
        }

        if (userObject.getId() == null) {
            throw new ValidationException("ID должен быть указан");
        }

        User oldUser = getUserMap().get(userObject.getId());

        if (oldUser == null) {
            throw new NotFoundException("Пользователь с id [" + userObject.getId() + "] - не найден");
        }


        if (!oldUser.getEmail().equalsIgnoreCase(userObject.getEmail())) {
            oldUser.setEmail(userObject.getEmail());
        }

        if (!oldUser.getLogin().equalsIgnoreCase(userObject.getLogin())) {
            oldUser.setLogin(oldUser.validationLogin(userObject.getLogin()));
        }

        if (!oldUser.getName().equalsIgnoreCase(userObject.getName())) {
            oldUser.setName(oldUser.validationName(userObject.getName()));
        }

        if (!oldUser.getBirthday().isEqual(userObject.getBirthday())) {
            oldUser.setBirthday(oldUser.validationBirthday(userObject.getBirthday()));
        }

        return oldUser;
    }

    @Override
    public Collection<User> getUsers() {
        return userMap.values();
    }

    @Override
    public void deleteUsers() {
        userMap.clear();
    }

    private long nextID() {
        long id = userMap.keySet()
                .stream()
                .mapToLong(k -> k)
                .max()
                .orElse(0);
        return ++id;
    }
}
