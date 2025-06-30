package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FriendStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@RequiredArgsConstructor
@Repository
public class FriendDbStorage implements FriendStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void addFriend(int id, int friendId) {
        String sql = "INSERT INTO friendship(user_id, friend_id) VALUES (?,?)";
        jdbcTemplate.update(sql, id, friendId);
    }

    @Override
    public void removeFriend(int id, int friendId) {
        String sql = "DELETE FROM friendship WHERE (user_id = ? AND friend_id = ?) OR (user_id = ? AND friend_id = ?)";
        jdbcTemplate.update(sql, id, friendId, friendId, friendId);
    }

    @Override
    public List<User> findAllFriends(int id) {

        String checkUserSql = "SELECT COUNT(*) FROM users WHERE user_id = ?";
        Integer count = jdbcTemplate.queryForObject(checkUserSql, Integer.class, id);

        if (count == null || count == 0) {
            throw new UserNotFoundException("Пользователь с id " + id + " не найден");
        }

        String sql = "SELECT u.user_id, u.email, u.login, u.name, u.birthday " +
                "FROM friendship AS f " +
                "INNER JOIN users AS u ON u.user_id = f.friend_id " +
                "WHERE f.user_id = ? " +
                "ORDER BY u.user_id";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeFriend(rs), id);
    }

    @Override
    public List<User> findCommonFriends(int id, int otherId) {
        String sql = "SELECT u.user_id, u.email, u.login, u.name, u.birthday " +
                "FROM friendship AS f " +
                "INNER JOIN friendship fr on fr.friend_id = f.friend_id " +
                "INNER JOIN users u on u.user_id = fr.friend_id " +
                "WHERE f.user_id = ? and fr.user_id = ? " +
                "AND f.friend_id <> fr.user_id AND fr.friend_id <> f.user_id";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeFriend(rs), id, otherId);
    }

    private User makeFriend(ResultSet rs) throws SQLException {
        return User.builder()
                .id(rs.getInt("user_id"))
                .email(rs.getString("email"))
                .login(rs.getString("login"))
                .name(rs.getString("name"))
                .birthday(rs.getDate("birthday").toLocalDate())
                .build();
    }
}
