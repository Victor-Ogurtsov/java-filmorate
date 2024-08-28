package ru.yandex.practicum.filmorate.DbStorageTest;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.storage.film.DbFilmStorage;
import ru.yandex.practicum.filmorate.storage.film.FilmResultSetExtractor;
import ru.yandex.practicum.filmorate.storage.film.FilmsResultSetExtractor;

import java.util.HashSet;
import java.util.LinkedHashSet;

@JdbcTest
@Import({DbFilmStorage.class, FilmResultSetExtractor.class, FilmsResultSetExtractor.class})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("DbFilmStorage")
public class DbFilmStorageTest {
    private final DbFilmStorage dbFilmStorage;
    public static final long TEST_FILM_ID = 1L;

    static Film getTestFilm() {
        Film film = new Film();
        film.setId(TEST_FILM_ID);
        film.setName("Фильм");
        film.setDescription("Описание");
        film.setReleaseDate("2015-10-11");
        film.setDuration(130);
        MPA mpa = new MPA();
        mpa.setId(2);
        mpa.setName("PG");
        film.setMpa(mpa);
        film.setGenres(new LinkedHashSet<>());
        Genre genre = new Genre(3, "Мультфильм");
        film.getGenres().add(genre);
        film.setIdsUsersWhoLiked(new HashSet<>());
        film.getIdsUsersWhoLiked().add(1L);
        return film;
    }

    @Test
    @DisplayName("Должен находить пользователя по id")
    public void should_return_film_when_find_by_id() {
        Film film = dbFilmStorage.getFilm(TEST_FILM_ID);

        Assertions.assertThat(film).usingRecursiveComparison().ignoringActualNullFields().isEqualTo(getTestFilm());
    }
}
