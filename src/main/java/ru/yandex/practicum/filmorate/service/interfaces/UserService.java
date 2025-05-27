package ru.yandex.practicum.filmorate.service.interfaces;

import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

public interface UserService {

    User createUser(User userObject);

    User updateUser(User userObject);

    Collection<User> getUsers();

    void deleteUsers();

    void addFriends(Long userId, Long friendId);

    void deleteFromFriends(Long userId, Long friendId);

    Collection<User> commonFriends(Long userId, Long otherId);

    Collection<User> usersFriends(Long userId);
}
