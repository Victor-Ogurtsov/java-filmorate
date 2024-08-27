package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
public class FilmsResultSetExtractor implements ResultSetExtractor<Collection<Film>> {

    @Override
    public Collection<Film> extractData(ResultSet rs) throws SQLException, DataAccessException {
        HashMap<Long,Film> films = new HashMap<>();

        while (rs.next()) {
            Long id = rs.getLong("ID");
            Long idUserPutLike = rs.getLong("USER_ID");
            Integer genreId = rs.getInt("GENRE_ID");

            if (films.get(id) == null) {
                Film film = new Film();
                film.setId(id);
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
                films.put(id, film);
            } else {
                if (genreId > 0) {
                    Genre nextGenre = new Genre();
                    nextGenre.setId(genreId);
                    nextGenre.setName(rs.getString("GENRE_NAME"));
                    films.get(id).getGenres().add(nextGenre);
                }
                if (idUserPutLike > 0) {
                    films.get(id).getIdsUsersWhoLiked().add(idUserPutLike);
                }
            }
        }
        return films.values();
    }
}
