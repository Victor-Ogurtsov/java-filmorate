package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

@Component
public class FilmRowMapper implements RowMapper<Film> {
    @Override
    public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
        Film film = new Film();
        film.setId(rs.getLong("ID"));
        film.setName(rs.getString("\"NAME\""));
        film.setDescription(rs.getString("DESCRIPTION"));
        film.setReleaseDate(rs.getString("RELEASEDATE"));
        film.setDuration(rs.getInt("DURATION"));
        return film;
    }
}
