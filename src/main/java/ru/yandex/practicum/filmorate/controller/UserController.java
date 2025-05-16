package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.DuplicatedException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final Map<Long, User> userMap = new HashMap<>();
    private User user;


    private long nextID() {
        long id = userMap.keySet()
                .stream()
                .mapToLong(k -> k)
                .max()
                .orElse(0);
        return ++id;
    }

    @PostMapping
    public User createUser(@RequestBody @Valid User userObject) {

        if (userObject == null) {
            throw new NullPointerException("Не корректная инициализация объекта типа \"User\"");
        }

        log.debug("Получен ID: {}", userObject.getID());
        log.debug("Получен email: {}", userObject.getEmail());
        log.debug("Получен login: {}", userObject.getLogin());
        log.debug("Получено name: {}", userObject.getName());
        log.debug("Получен birthday: {}", userObject.getBirthday());

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

        user = new User();

        user.setID(nextID());
        user.setEmail(userObject.getEmail());
        user.setLogin(user.validationLogin(userObject.getLogin()));
        user.setName(user.validationName(userObject.getName()));
        user.setBirthday(user.validationBirthday(userObject.getBirthday()));

        userMap.put(user.getID(), user);

        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody @Valid User userObject) {

        if (userObject == null) {
            throw new ValidationException("Не корректная инициализация объекта типа \"User\"");
        }

        if (userObject.getID() == null) {
            throw new ValidationException("ID должен быть указан");
        }

        log.debug("Получен ID: {}", userObject.getID());
        log.debug("Получен email: {}", userObject.getEmail().trim());
        log.debug("Получен login: {}", userObject.getLogin().trim());
        log.debug("Получено name: {}", userObject.getName().trim());
        log.debug("Получена birthdayDate: {}", userObject.getBirthday());

        User oldUSer = userMap.get(userObject.getID());

        if (oldUSer == null) {
            createUser(userObject);
        }

        boolean emailExists = userMap.values()
                .stream()
                .anyMatch(u -> u.getEmail().trim().equalsIgnoreCase(userObject.getEmail().trim()));

        if (emailExists) {
            throw new DuplicatedException("Email адрес: " + userObject.getEmail() + " - занят");
        }

        boolean loginExists = userMap.values()
                .stream()
                .anyMatch(u -> u.getLogin().trim().equalsIgnoreCase(userObject.getLogin().trim()));

        if (loginExists) {
            throw new DuplicatedException("Логин: " + userObject.getLogin() + " - занят");
        }

        user.setEmail(!(userObject.getEmail().equalsIgnoreCase(oldUSer.getEmail())) ?
                userObject.getEmail() : oldUSer.getEmail());

        user.setLogin(oldUSer.validationLogin(!(userObject.getLogin().equalsIgnoreCase(oldUSer.getLogin())) ?
                userObject.getLogin() : oldUSer.getLogin()));

        user.setName(oldUSer.validationName(!(userObject.getName().equalsIgnoreCase(oldUSer.getName())) ?
                userObject.getName() : oldUSer.getName()));

        user.setBirthday(oldUSer.validationBirthday(!(userObject.getBirthday().isEqual(oldUSer.getBirthday())) ?
                userObject.getBirthday() : oldUSer.getBirthday()));

        return oldUSer;
    }

    @GetMapping
    public Collection<User> getUsers() {

        /*if (userMap.isEmpty()) {
            throw new NullPointerException("Список пользователей пуст");
        }*/
        return userMap.values();
    }

    @DeleteMapping
    public void deleteUsers() {

        /*if (userMap.isEmpty()) {
            throw new NullPointerException("Список пользователей пуст");
        }*/
        userMap.clear();
    }
}
