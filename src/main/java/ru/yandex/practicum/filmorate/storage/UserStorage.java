package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

public interface UserStorage {

    public User createUser(User userObject);

    public User updateUser(User userObject);

    public Collection<User> getUsers();

    public void deleteUsers();
}
