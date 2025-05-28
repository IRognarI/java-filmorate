package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.interfaces.UserService;
import ru.yandex.practicum.filmorate.storage.interfaces.UserStorage;

import java.util.*;

@Slf4j
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

        log.debug("Получены данные: {}, {}", userId, friendId);

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

        log.info("Кол-во друзей у user до добавления нового [{}]" +
                        "\nКол-во друзей у targetUser до добавления нового [{}]", user.getFriends().size(),
                targetUser.getFriends().size());

        user.getFriends().add(targetUser);
        targetUser.getFriends().add(user);

        log.info("Кол-во друзей у user после добавления нового [{}]" +
                        "\nКол-во друзей у targetUser после добавления нового [{}]", user.getFriends().size(),
                targetUser.getFriends().size());
    }

    @Override
    public Collection<User> commonFriends(Long userId, Long otherId) throws ValidationException, NotFoundException {

        log.debug("Полученные данные: {}, {}", userId, otherId);

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

        log.debug("Полученные данные: {}, {}", userId, friendId);

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

        log.info("Кол-во друзей у user до удаления [{}]" +
                        "\nКол-во друзей у targetUser до удаления [{}]", user.getFriends().size(),
                targetUser.getFriends().size());

        user.getFriends().remove(targetUser.getId());
        targetUser.getFriends().remove(user.getId());

        log.info("Кол-во друзей у user после удаления [{}]" +
                        "\nКол-во друзей у targetUser после удаления [{}]", user.getFriends().size(),
                targetUser.getFriends().size());
    }

    @Override
    public Collection<User> usersFriends(Long userId) throws ValidationException, NotFoundException {

        log.debug("Полученное ID пользователя: {}", userId);

        if (userId == null) throw new ValidationException("Укажите ID пользователя");

        if (userId <= 0) throw new ValidationException("ID не может быть [" + userId + "]");

        User user = userStorage.getUserMap().get(userId);

        if (user == null) {
            throw new NotFoundException("Пользователь с ID [" + userId + "] - не найден");
        }
        return user.getFriends();
    }
}