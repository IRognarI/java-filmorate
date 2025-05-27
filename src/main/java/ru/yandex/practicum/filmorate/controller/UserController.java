package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final InMemoryUserStorage inMemoryUserStorage;

    @PostMapping
    public User createUser(@RequestBody @Valid User userObject) {
        return inMemoryUserStorage.createUser(userObject);
    }

    @PutMapping
    public User updateUser(@RequestBody @Valid User userObject) {
        return inMemoryUserStorage.updateUser(userObject);
    }

    @GetMapping
    public Collection<User> getUsers() {
        return inMemoryUserStorage.getUsers();
    }

    @DeleteMapping
    public void deleteUsers() {
        inMemoryUserStorage.deleteUsers();
    }
}
