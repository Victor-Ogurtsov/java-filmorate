package ru.yandex.practicum.filmorate.DbStorageTest;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genres.DbGenresStorage;
import ru.yandex.practicum.filmorate.storage.genres.GenreRowMapper;

import java.util.List;

@JdbcTest
@Import({DbGenresStorage.class, GenreRowMapper.class})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("DbGenresStorage")
public class DbGenresStorageTest {
    private final DbGenresStorage dbGenresStorage;

    @Test
    @DisplayName("Должен вернуть список жанров добавленных из test-data.sql")
    public void should_return_genre_list() {
        List<Genre> genres = dbGenresStorage.getGenresList();

        Assertions.assertThat(genres.size()).usingRecursiveComparison().ignoringActualNullFields().isEqualTo(6);
    }

    @Test
    @DisplayName("Должен вернуть жанр по id")
    public void should_return_genre_by_id() {
        Genre genre = new Genre(1, "Комедия");

        Assertions.assertThat(dbGenresStorage.getGenre(1)).usingRecursiveComparison()
                .ignoringActualNullFields().isEqualTo(genre);
    }
}
