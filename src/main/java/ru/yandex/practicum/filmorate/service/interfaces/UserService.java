package ru.yandex.practicum.filmorate.service.interfaces;

import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

public interface UserService {

    void addFriends(long userId, long friendId);

    void deleteFromFriends(long userId, long friendId);

    Collection<User> commonFriends(long userId, long otherId);

    Collection<User> usersFriends(long userId);
}
