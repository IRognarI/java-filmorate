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
    public void addFriends(Long userId, Long friendId) {

        if (userId == null) throw new ValidationException("Укажите ваш ID");

        if (userId <= 0) throw new ValidationException("ID не может быть [" + userId + "]");

        if (friendId == null) throw new ValidationException("Укажите ID пользователя," +
                "которого желаете добавить в друзья");

        if (friendId <= 0) throw new ValidationException("ID пользователя не может быть [" + userId + "]");

        if (!userStorage.getUserMap().containsKey(userId)) {
            throw new NotFoundException("Пользователь с ID [" + userId + "] - не найден");
        }

        if (!userStorage.getUserMap().containsKey(friendId)) {
            throw new NotFoundException("Не возможно добавить в друзья! Пользователь с ID [" + friendId + "]" +
                    " - не существует");
        }

        User user = userStorage.getUserMap().get(userId);
        User targetUser = userStorage.getUserMap().get(friendId);

        user.getFriends().add(friendId);
        targetUser.getFriends().add(userId);
    }

    @Override
    public void deleteFromFriends(Long userId, Long friendId) {

        if (userId == null) throw new ValidationException("Укажите ваш ID");

        if (userId <= 0) throw new ValidationException("ID не может быть [" + userId + "]");

        if (friendId == null) throw new ValidationException("Укажите ID пользователя," +
                "которого желаете удалить из друзей");

        if (friendId <= 0) throw new ValidationException("ID пользователя не может быть [" + friendId + "]");

        if (!userStorage.getUserMap().containsKey(userId)) {
            throw new NotFoundException("Пользователь с ID [" + userId + "] - не найден");
        }

        if (!userStorage.getUserMap().containsKey(friendId)) {
            throw new NotFoundException("Пользователя с ID [" + friendId + "] - не существует");
        }

        User user = userStorage.getUserMap().get(userId);
        User targetUser = userStorage.getUserMap().get(friendId);

        user.getFriends().remove(targetUser.getId());
        targetUser.getFriends().remove(user.getId());
    }

    @Override
    public Collection<User> commonFriends(Long userId, Long otherId) {

        if (userId == null) throw new ValidationException("Укажите ID пользователя");

        if (userId <= 0) throw new ValidationException("ID не может быть [" + userId + "]");

        if (otherId == null) throw new ValidationException("Укажите ID другого пользователя");

        if (otherId <= 0) throw new ValidationException("ID пользователя не может быть [" + userId + "]");

        if (!userStorage.getUserMap().containsKey(userId)) {
            throw new NotFoundException("Пользователь с ID [" + userId + "] - не найден");
        }

        if (!userStorage.getUserMap().containsKey(otherId)) {
            throw new NotFoundException("Пользователь с ID [" + otherId + "] - не существует");
        }

        User firstUser = userStorage.getUserMap().get(userId);
        User secondUser = userStorage.getUserMap().get(otherId);

        Collection<User> commonFriends = new ArrayList<>();

        for (Long id : userStorage.getUserMap().keySet()) {

            for (Long firstUserId : firstUser.getFriends()) {

                for (Long secondUserId : secondUser.getFriends()) {

                    if (id.equals(firstUserId) && id.equals(secondUserId)) {
                        commonFriends.add(userStorage.getUserMap().get(id));
                    }
                }
            }
        }

        if (commonFriends.isEmpty()) {
            throw new NotFoundException("Ошибка поиска общих друзей! Друзья не найдены");
        }
        return commonFriends.stream().sorted(Comparator.comparing(User::getName)).toList();
    }

    @Override
    public Collection<User> usersFriends(Long userId) {

        if (userId == null) throw new ValidationException("Укажите ID пользователя");

        if (userId <= 0) throw new ValidationException("ID не может быть [" + userId + "]");

        User user = userStorage.getUserMap().get(userId);

        Collection<User> friends = new ArrayList<>();

        for (Long id : userStorage.getUserMap().keySet()) {

            for (Long friendId : user.getFriends()) {

                if (id.equals(friendId)) {
                    friends.add(userStorage.getUserMap().get(friendId));
                }
            }
        }
        return friends.stream().sorted(Comparator.comparing(User::getName)).toList();
    }
}