package ru.yandex.practicum.filmorate.error;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ErrorResponse {
    private String error;
    private String description;
}