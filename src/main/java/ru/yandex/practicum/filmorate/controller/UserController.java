package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.interfaces.UserService;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    public User createUser(@RequestBody @Valid User userObject) {
        return userService.createUser(userObject);
    }

    @PutMapping
    public User updateUser(@RequestBody @Valid User userObject) {
        return userService.updateUser(userObject);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriends(@PathVariable(name = "id") Long userId,
                           @PathVariable(name = "friendId") Long friendId) {

        userService.addFriends(userId, friendId);
    }

    @GetMapping
    public Collection<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}/friends")
    public Collection<User> usersFriends(@PathVariable(name = "id") Long userId) {
        return userService.usersFriends(userId);
    }

    @GetMapping("{id}/friends/common/{otherId}")
    public Collection<User> commonFriends(@PathVariable(name = "id") Long userId,
                                          @PathVariable(name = "otherId") Long otherId) {

        return userService.commonFriends(userId, otherId);
    }

    @DeleteMapping
    public void deleteUsers() {
        userService.deleteUsers();
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFromFriends(@PathVariable(name = "id") Long userId,
                                  @PathVariable(name = "friendId") Long friendId) {

        userService.deleteFromFriends(userId, friendId);
    }
}
