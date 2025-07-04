package ru.yandex.practicum.filmorate.annotation;

import java.time.LocalDate;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Валидатор для проверки даты релиза. Проверяет, что указанная дата не раньше минимальной допустимой даты.
 */
public class ReleaseDateValidator implements ConstraintValidator<ReleaseDate, LocalDate> {

    /**
     * Минимальная допустимая дата релиза.
     */
    private LocalDate minDate;


    /**
     * Инициализация валидатора. Парсит минимальную дату из аннотации.
     */
    @Override
    public void initialize(ReleaseDate annotation) {
        this.minDate = LocalDate.parse(annotation.value());
    }

    /**
     * Проверка валидности даты. Возвращает true, если дата не раньше минимальной допустимой.
     */
    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        return !value.isBefore(minDate);
    }
}
