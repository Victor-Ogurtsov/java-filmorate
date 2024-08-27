package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedHashSet;

@Component
public class FilmResultSetExtractor implements ResultSetExtractor<Film> {

    @Override
    public Film extractData(ResultSet rs) throws SQLException, DataAccessException {
        Film film = null;
        while (rs.next()) {
            Long idUserPutLike =rs.getLong("USER_ID");
            Integer genreId = rs.getInt("GENRE_ID");

            if (film == null) {
                film = new Film();
                film.setId(rs.getLong("ID"));
                film.setName(rs.getString("FILM_NAME"));
                film.setDescription(rs.getString("DESCRIPTION"));
                film.setReleaseDate(rs.getString("RELEASEDATE"));
                film.setDuration(rs.getInt("DURATION"));
                MPA mpa = new MPA();
                mpa.setId(rs.getInt("MPA"));
                mpa.setName(rs.getString("MPA_NAME"));
                film.setMpa(mpa);
                film.setIdsUsersWhoLiked(new HashSet<>());
                if (idUserPutLike > 0) {
                    film.getIdsUsersWhoLiked().add(idUserPutLike);
                }
                film.setGenres(new LinkedHashSet<>());
                if (genreId > 0) {
                    Genre genre = new Genre();
                    genre.setId(genreId);
                    genre.setName(rs.getString("GENRE_NAME"));
                    film.getGenres().add(genre);
                }
            } else {
                if (genreId > 0) {
                    Genre nextGenre = new Genre();
                    nextGenre.setId(rs.getInt("GENRE_ID"));
                    nextGenre.setName(rs.getString("GENRE_NAME"));
                    film.getGenres().add(nextGenre);
                }
                if (idUserPutLike > 0) {
                    film.getIdsUsersWhoLiked().add(rs.getLong("USER_ID"));
                }
            }
        }
        return film;
    }
}