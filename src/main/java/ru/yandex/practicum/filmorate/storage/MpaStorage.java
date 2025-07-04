package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.*;

public interface MpaStorage {
    List<Mpa> findAllMpa();

    Optional<Mpa> findMpaById(int id);
}
