package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
<<<<<<< HEAD
import ru.yandex.practicum.filmorate.service.interfaces.UserService;
=======
import ru.yandex.practicum.filmorate.service.UserService;
>>>>>>> b13c2dd (Произведен рефакторинг кода: 1. Исправлены валидаторы в POJO (заменены на аннотации), также согласно условию ТЗ добавлены новые сущности + реализована работа с БД Н2. Изменена структура дирректорий проекта. Добавлены новые интерфейсы и контроллеры. Добавлены DAO классы (реализующие новые интерфейсы). Приложение проверено через Postman. Все тесты пройдены успешно.)

import java.util.*;
import javax.validation.Valid;

<<<<<<< HEAD
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
=======
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("users")
>>>>>>> b13c2dd (Произведен рефакторинг кода: 1. Исправлены валидаторы в POJO (заменены на аннотации), также согласно условию ТЗ добавлены новые сущности + реализована работа с БД Н2. Изменена структура дирректорий проекта. Добавлены новые интерфейсы и контроллеры. Добавлены DAO классы (реализующие новые интерфейсы). Приложение проверено через Postman. Все тесты пройдены успешно.)
public class UserController {
    private final UserService userService;

    @PostMapping
<<<<<<< HEAD
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody @Valid User userObject) {
        return userService.createUser(userObject);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public User updateUser(@RequestBody @Valid User userObject) {
        return userService.updateUser(userObject);
    }

    @PutMapping("/{id}/friends/{friendId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addFriends(@PathVariable(name = "id") Long userId,
                           @PathVariable(name = "friendId") Long friendId) {

        userService.addFriends(userId, friendId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}/friends")
    @ResponseStatus(HttpStatus.OK)
    public Collection<User> usersFriends(@PathVariable(name = "id") Long userId) {
        return userService.usersFriends(userId);
    }

    @GetMapping("{id}/friends/common/{otherId}")
    @ResponseStatus(HttpStatus.OK)
    public Collection<User> commonFriends(@PathVariable(name = "id") Long userId,
                                          @PathVariable(name = "otherId") Long otherId) {

        return userService.commonFriends(userId, otherId);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUsers() {
        userService.deleteUsers();
=======
    public User create(@Valid @RequestBody User user) {
        log.info("POST / user / {}", user.getLogin());
        userService.create(user);
        return user;
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        log.info("PUT / user / {}", user.getLogin());
        userService.update(user);
        return user;
    }

    @GetMapping
    public List<User> findAll() {
        log.info("GET / users");
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public User findUserById(@PathVariable("id") int id) {
        log.info("GET / users / {}", id);
        return userService.findUserById(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable("id") int id, @PathVariable("friendId") int friendId) {
        log.info("PUT / {} / friends / {}", id, friendId);
        userService.addFriend(id, friendId);
>>>>>>> b13c2dd (Произведен рефакторинг кода: 1. Исправлены валидаторы в POJO (заменены на аннотации), также согласно условию ТЗ добавлены новые сущности + реализована работа с БД Н2. Изменена структура дирректорий проекта. Добавлены новые интерфейсы и контроллеры. Добавлены DAO классы (реализующие новые интерфейсы). Приложение проверено через Postman. Все тесты пройдены успешно.)
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void removeFriend(@PathVariable("id") int id, @PathVariable("friendId") int friendId) {
        log.info("PUT / {} / friends / {}", id, friendId);
        userService.removeFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> findAllFriends(@PathVariable("id") int id) {
        log.info("GET / {} / friends", id);
        return userService.findAllFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> findCommonFriends(@PathVariable("id") int id, @PathVariable("otherId") int otherId) {
        log.info("GET / {} / friends / common / {}", id, otherId);
        return userService.findCommonFriends(id, otherId);
    }
}
