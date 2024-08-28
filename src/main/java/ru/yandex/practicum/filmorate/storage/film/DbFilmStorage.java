package ru.yandex.practicum.filmorate.storage.film;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Collection;

@Repository
@Primary
@RequiredArgsConstructor
public class DbFilmStorage implements FilmStorage {
    private final JdbcTemplate jdbc;
    private final FilmResultSetExtractor filmResultSetExtractor;
    private final CollectionFilmsResultSetExtractor collectionFilmsResultSetExtractor;

    @Override
    public Collection<Film> getAllFilms() {
        String sql = "SELECT f.ID, f.FILM_NAME, f.DESCRIPTION, f.RELEASEDATE, f.DURATION, f.MPA, m.MPA_NAME, fg.GENRE_ID, gn.GENRE_NAME, l.USER_ID \n" +
                "FROM FILMS f\n" +
                "LEFT JOIN FILMS_GENRES fg ON f.ID = fg.FILM_ID \n" +
                "LEFT JOIN GENRE_NAMES gn ON gn.ID = fg.GENRE_ID \n" +
                "LEFT JOIN MPA m ON f.MPA = m.ID\n" +
                "LEFT JOIN LIKES l ON f.ID = l.FILM_ID";
        return jdbc.query(sql, collectionFilmsResultSetExtractor);
    }

    @Override
    public Film addFilm(Film film) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO FILMS (FILM_NAME, DESCRIPTION, RELEASEDATE," +
                            " DURATION, MPA) VALUES (?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setObject(1, film.getName());
            ps.setObject(2, film.getDescription());
            ps.setObject(3, film.getReleaseDate());
            ps.setObject(4, film.getDuration());
            ps.setObject(5, film.getMpa().getId());

            return ps;
        }, keyHolder);

        return getFilm(keyHolder.getKeyAs(Long.class));
    }

    public Film updateFilm(Film film) {
        String sql = "UPDATE FILMS SET FILM_NAME = ?, DESCRIPTION = ?, RELEASEDATE = ?, DURATION = ?, MPA = ? WHERE ID = ?";
        jdbc.update(sql, film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getMpa().getId(), film.getId());
        return getFilm(film.getId());
    }

    @Override
    public Film getFilm(Long filmId) {
        String sql = "SELECT f.ID, f.FILM_NAME, f.DESCRIPTION, f.RELEASEDATE, f.DURATION, f.MPA, m.MPA_NAME, fg.GENRE_ID, gn.GENRE_NAME, l.USER_ID \n" +
                "FROM FILMS f\n" +
                "LEFT JOIN FILMS_GENRES fg ON f.ID = fg.FILM_ID \n" +
                "LEFT JOIN GENRE_NAMES gn ON gn.ID = fg.GENRE_ID \n" +
                "LEFT JOIN MPA m ON f.MPA = m.ID\n" +
                "LEFT JOIN LIKES l ON f.ID = l.FILM_ID\n" +
                "WHERE f.ID = ?";
        return jdbc.query(sql, filmResultSetExtractor, filmId);
    }
}
