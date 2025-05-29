package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.interfaces.UserService;
import ru.yandex.practicum.filmorate.storage.interfaces.UserStorage;

import java.util.*;

@Service
@RequiredArgsConstructor
@Primary
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;

    @Override
    public User createUser(User userObject) {
        return userStorage.createUser(userObject);
    }

    @Override
    public User updateUser(User userObject) {
        return userStorage.updateUser(userObject);
    }

    @Override
    public Collection<User> getUsers() {
        return userStorage.getUsers();
    }

    @Override
    public void deleteUsers() {
        userStorage.deleteUsers();
    }

    @Override
    public void addFriends(Long userId, Long friendId) throws ValidationException, NotFoundException {

        if (userId == null || friendId == null) {
            throw new ValidationException("Id пользователей должны быть указаны");
        }

        if (userId <= 0 || friendId <= 0) {
            throw new ValidationException("Id пользователй должны быть больше 0");
        }

        User firstUser = userStorage.getUserMap().get(userId);

        if (firstUser == null) {
            throw new NotFoundException("Пользователь с id [" + userId + "] - не найден");
        }

        User secondUser = userStorage.getUserMap().get(friendId);

        if (secondUser == null) {
            throw new NotFoundException("Пользователь с id [" + friendId + "] - не найден");
        }

        firstUser.getFriends().add(secondUser);
        secondUser.getFriends().add(firstUser);
    }

    @Override
    public Collection<User> commonFriends(Long userId, Long otherId) throws ValidationException, NotFoundException {

        if (userId == null || otherId == null) {
            throw new ValidationException("ID пользователей должны быть указаны");
        }

        if (userId <= 0 || otherId <= 0) {
            throw new ValidationException("ID пользователя должно быть больше 0");
        }

        User firstUser = userStorage.getUserMap().get(userId);

        if (firstUser == null) {
            throw new NotFoundException("Пользователя с ID [" + userId + "] - не существует");
        }

        User secondUser = userStorage.getUserMap().get(otherId);

        if (secondUser == null) {
            throw new NotFoundException("Пользователя с ID [" + otherId + "] - не существует");
        }

        Set<User> userSet = new TreeSet<>(firstUser.getFriends());
        userSet.retainAll(firstUser.getFriends());

        return userSet;
    }

    @Override
    public void deleteFromFriends(Long userId, Long friendId) throws ValidationException, NotFoundException {

        if (userId == null || friendId == null) {
            throw new ValidationException("Id пользователей должен быть указан");
        }

        if (userId <= 0 || friendId <= 0) {
            throw new ValidationException("Id пользователей должен быть больше 0");
        }

        User firstUser = userStorage.getUserMap().get(userId);

        if (firstUser == null) {
            throw new NotFoundException("Пользователь с id [" + userId + "] - не найден");
        }

        User secondUser = userStorage.getUserMap().get(friendId);

        if (secondUser == null) {
            throw new NotFoundException("Пользователь с id [" + friendId + "] - не найден");
        }

        firstUser.getFriends().remove(secondUser);
        secondUser.getFriends().remove(firstUser);
    }

    @Override
    public Collection<User> usersFriends(Long userId) throws ValidationException, NotFoundException {

        if (userId == null) throw new ValidationException("Укажите ID пользователя");

        if (userId <= 0) throw new ValidationException("ID не может быть [" + userId + "]");

        User user = userStorage.getUserMap().get(userId);

        if (user == null) {
            throw new NotFoundException("Пользователь с ID [" + userId + "] - не найден");
        }

        return user.getFriends();
    }
}