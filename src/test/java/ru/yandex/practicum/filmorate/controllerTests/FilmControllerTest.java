/*package ru.yandex.practicum.filmorate.controllerTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

@SpringBootTest
public class FilmControllerTest {

    FilmController filmController;

    @BeforeEach
    void createFilmController() {
       filmController = new FilmController(new FilmService(new InMemoryFilmStorage(),
               new UserService(new InMemoryUserStorage(), null),null, null,
               null));
    }

    @Test
    void shouldThrowValidationExceptionThenAddFilmAndFilmEqualNull() {
        Assertions.assertThrows(ValidationException.class, () -> filmController.addFilm(null), "не выброшено исключение " +
                "на описание длинной 201 символ");
    }

    @Test
    void shouldReturnTrueThenAddNewFilm() {
        Film film = filmController.addFilm(new Film(null, "name", "description", "1895-12-28",
                140, null, null,null));

        Assertions.assertTrue(filmController.getAllFilms().size() == 1, "Фильм не добавлен в приложение");
    }
    /*
    @Test
    void shouldThrowValidationExceptionThenAddNewFilmAndDescription201Characters() {
        String description = "В 2296 году, более 200 лет спустя после ядерной войны, потомки привилегированных и богатых" +
                " живут в автономных благоустроенных бункерах, а остальное человечество выживает в жёстких условиях. Умница и...";
        Film film = new Film(null, "Fallout", description, "1895-12-28", 40, new HashSet<>());

        Assertions.assertThrows(ValidationException.class, () -> filmController.addFilm(film), "не выброшено исключение " +
                "на описание длинной 201 символ");
    }

    @Test
    void shouldThrowValidationExceptionThenAddNewFilmAndReleaseDate1895_12_27() {
        Film film = new Film(null, "name", "description", "1895-12-27", 40, new HashSet<>());

        Assertions.assertThrows(ValidationException.class, () -> filmController.addFilm(film), "Не выброшено исключение" +
                " при дате релиза 1895-12-27");
    }

    @Test
    void shouldThrowValidationExceptionThenUpdateFilmAndFilmEqualNull() {
        Assertions.assertThrows(ValidationException.class, () -> filmController.updateFilm(null), "не выброшено исключение " +
                "на описание длинной 201 символ");
    }

    @Test
    void shouldReturnTrueThenUpdateFilm() {
        Film film = filmController.addFilm(new Film(null, "name", "description", "1895-12-28", 40, new HashSet<>()));
        film.setName("NewName");
        filmController.updateFilm(film);

        Assertions.assertTrue(filmController.getAllFilms().contains(film), "Фильм не добавлен в приложение");
    }

    @Test
    void shouldThrowValidationExceptionThenUpdateFilmAndDescription201Characters() {
        Film film = filmController.addFilm(new Film(null, "name", "description", "1895-12-28", 40, new HashSet<>()));
        String description = "В 2296 году, более 200 лет спустя после ядерной войны, потомки привилегированных и богатых" +
                " живут в автономных благоустроенных бункерах, а остальное человечество выживает в жёстких условиях. Умница и...";
        film.setDescription(description);

        Assertions.assertThrows(ValidationException.class, () -> filmController.updateFilm(film), "не выброшено исключение " +
                "на описание длинной 201 символ");
    }

    @Test
    void shouldThrowValidationExceptionThenUpdateFilmAndReleaseDate1895_12_27() {
        Film film = filmController.addFilm(new Film(null, "name", "description", "1895-12-28", 40, new HashSet<>()));
        film.setReleaseDate("1895-12-27");

        Assertions.assertThrows(ValidationException.class, () -> filmController.updateFilm(film), "Не выброшено исключение" +
                " при дате релиза 1895-12-27");
    }

    @Test
    void shouldReturn2ThenAdd2Films() {
        filmController.addFilm(new Film(null, "name1", "description1", "1895-12-28", 40, new HashSet<>()));
        filmController.addFilm(new Film(null, "name2", "description2", "1895-12-28", 50, new HashSet<>()));

        Assertions.assertEquals(2, filmController.getAllFilms().size(), "количество фильмов в приложении не " +
                "соответствует количеству добавленных фильмов");
    }
}
*/