package ru.yandex.practicum.filmorate.storage.film;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Collection;
import java.util.List;

@Repository
@Primary
@RequiredArgsConstructor
public class DbFilmStorage implements FilmStorage{
    private final JdbcTemplate jdbc;
    private final FilmRowMapper mapper;

    @Override
    public Collection<Film> getAllFilms() {
        return jdbc.query("SELECT * FROM FILMS", mapper);
    }

    @Override
    public long getNextId() {
        return 0;
    }

    @Override
    public Film addFilm(Film film) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO films (\"NAME\", DESCRIPTION, RELEASEDATE, DURATION) VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setObject(1, film.getName());
            ps.setObject(2, film.getDescription());
            ps.setObject(3, film.getReleaseDate());
            ps.setObject(4, film.getDuration());

            return ps;
        }, keyHolder);

        film.setId(keyHolder.getKeyAs(Long.class));
        return film;
    }

    @Override
    public boolean isFilmExists(Long id) {
        return false;
    }

    @Override
    public Film getFilm(Long filmId) {

        return jdbc.queryForObject("SELECT * FROM FILMS WHERE ID = ?", mapper, filmId);
    }
}
