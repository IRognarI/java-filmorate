package ru.yandex.practicum.filmorate;

import ch.qos.logback.classic.Level;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.yandex.practicum.filmorate.model.Film;

@SpringBootApplication
public class FilmorateApplication {

    public static void main(String[] args) {

        /*Film film = Film.builder().build();
        film.getLog().setLevel(Level.ERROR);*/


        SpringApplication.run(FilmorateApplication.class, args);
    }

}
