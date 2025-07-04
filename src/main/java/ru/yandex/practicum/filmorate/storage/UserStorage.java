package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

public interface UserStorage {
    User create(User user);

    User update(User user);

    List<User> findAll();

    Optional<User> findUserById(int id);
}