package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.interfaces.UserService;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final InMemoryUserStorage userStorage;

    @Override
    public void addFriends(long userId, long friendId) {

    }

    @Override
    public void deleteFromFriends(long userId, long friendId) {

    }

    @Override
    public Collection<User> commonFriends(long userId, long otherId) {
        return List.of();
    }

    @Override
    public Collection<User> usersFriends(long userId) {
        return List.of();
    }
}
