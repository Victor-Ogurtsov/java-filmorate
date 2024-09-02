package ru.yandex.practicum.filmorate.storage.genres;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class DbGenresStorage {
    private final JdbcTemplate jdbc;
    private final GenreRowMapper genreRowMapper;

    public List<Genre> getGenresList() {
        return jdbc.query("SELECT * FROM genre_names", genreRowMapper);
    }

    public Genre getGenre(Integer id) {
        return jdbc.queryForObject("SELECT * FROM GENRE_NAMES WHERE ID = ?", genreRowMapper, id);
    }

    public boolean isGenreIdExistsInSet(LinkedHashSet<Genre> genres) {
        boolean isIdExists = false;
        for (Genre genre : genres) {
            isIdExists = getGenresList().stream().anyMatch(genre1 -> Objects.equals(genre1.getId(), genre.getId()));
            if (isIdExists) {
                return isIdExists;
            }
        }
        return isIdExists;
    }

    public boolean isGenreIdExists(Integer id) {
        return getGenresList().stream().anyMatch(genre -> Objects.equals(genre.getId(), id));
    }

    public void addGenresForFilm(Film film) {
        for (Genre value : film.getGenres()) {
            jdbc.update("INSERT INTO films_genres (film_id, genre_id) VALUES (?, ?)",
                    film.getId(),
                    value.getId()
            );
        }
    }

    public void updateGenresForFilm(Film film) {
        deleteGenresForFilm(film);
        addGenresForFilm(film);
    }

    public void deleteGenresForFilm(Film film) {
        jdbc.update("DELETE FILMS_GENRES WHERE FILM_ID = ?", film.getId());
    }
}
