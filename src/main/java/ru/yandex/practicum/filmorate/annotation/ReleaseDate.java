package ru.yandex.practicum.filmorate.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;

/**
 * Аннотация для валидации даты релиза. Проверяет, что указанная дата не раньше заданной минимальной даты.
 */
@NotNull
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ReleaseDateValidator.class)
public @interface ReleaseDate {

    /**
     * Сообщение об ошибке
     */
    String message() default "Введите дату релиза не ранее {value}";

    /**
     * Группы валидации
     */
    Class<?>[] groups() default {};

    /**
     * Метод позволяет указать один или несколько классов, которые будут расширять функциональность валидации
     */
    Class<? extends Payload>[] payload() default {};

    /**
     * Минимальная допустимая дата в формате YYYY-MM-DD. Обязательный параметр для указания.
     */
    String value();
}

