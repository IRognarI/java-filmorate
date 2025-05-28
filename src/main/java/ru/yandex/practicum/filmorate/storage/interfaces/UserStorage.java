package ru.yandex.practicum.filmorate.storage.interfaces;

import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

public interface UserStorage {

    User createUser(User userObject);

    User updateUser(User userObject);

    Collection<User> getUsers();

    void deleteUsers();

    Map<Long, User> getUserMap();
}
