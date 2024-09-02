package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FilmComparator;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genres.DbGenresStorage;
import ru.yandex.practicum.filmorate.storage.like.DbLikeStorage;
import ru.yandex.practicum.filmorate.storage.mpa.DbMpaStorage;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.time.Instant;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserService userService;
    private final DbMpaStorage dbMpaStorage;
    private final DbGenresStorage dbGenresStorage;
    private final DbLikeStorage dbLikeStorage;


    public Collection<Film> getAllFilms() {
        log.info("Возврат списка фильмов");
        return filmStorage.getAllFilms();
    }

    public Film addFilm(Film film) {
        checkFilm(film);
        Film addedFilm = filmStorage.addFilm(film);
        log.info("Добавлен фильм с id={}", addedFilm.getId());
        film.setId(addedFilm.getId());
        if (film.getGenres() != null) {
            dbGenresStorage.addGenresForFilm(film);
            log.info("Для фильма с id={} добавлены жанры с id: {}", addedFilm.getId(), addedFilm.getGenres().stream().map(Genre::getId).toList());
        }
        return filmStorage.getFilm(addedFilm.getId());
    }

    public Film updateFilm(Film film) {
        checkFilm(film);
        if (film.getId() == null) {
            throw new ValidationException("Id должен быть указан");
        }
        checkFilmFindById(film.getId());
        if (film.getGenres() != null) {
            dbGenresStorage.updateGenresForFilm(film);
            log.info("Для фильма с id={} обновлены жанры с id: {}", film.getId(), film.getGenres().stream().map(Genre::getId).toList());
        }
        log.info("Изменен фильм с id={}", film.getId());
        return filmStorage.updateFilm(film);
    }

    public Film getFilm(Long filmId) {
        log.info("Возврат фильма с id={}", filmId);
        checkFilmFindById(filmId);
        return filmStorage.getFilm(filmId);
    }

    private void checkFilm(Film film) {
        if (film == null) {
            throw new ValidationException("film equal null");
        } else if (film.getDescription().length() > 200) {
            throw new ValidationException("Максимальная длина описания — 200 символов");
        } else if (Instant.parse(film.getReleaseDate() + "T00:00:00Z").isBefore(Instant.parse("1895-12-28T00:00:00Z"))) {
            throw new ValidationException("Дата релиза — не раньше 28 декабря 1895 года");
        } else if (dbMpaStorage.isMpaIdExists(film.getMpa().getId())) {
            throw new ValidationException("id mpa = " + film.getMpa().getId() + " указан неверно");
        } else if (film.getGenres() != null && !dbGenresStorage.isGenreIdExistsInSet(film.getGenres())) {
            throw new ValidationException("id жанра " + film.getGenres().stream().map(Genre::getId).toList() + " указан неверно");
        }
        if (film.getIdsUsersWhoLiked() == null) {
            film.setIdsUsersWhoLiked(new HashSet<>());
        }
    }

    public void likeIt(Long id, Long userId) {
        checkFilmFindById(id);
        userService.checkUserFindById(userId);
        dbLikeStorage.likeIt(id, userId);
        log.info("Фильм с id = {} получил лайк от пользователя с id = {}", id, userId);
    }

    public void deleteLike(Long id, Long userId) {
        checkFilmFindById(id);
        userService.checkUserFindById(userId);
        dbLikeStorage.deleteLike(id, userId);
        log.info("У фильма с id = {}  удален лайк от пользователя с id = {}", id, userId);
    }

    public Collection<Film> getTopFilms(Long count) {
        log.info("Возврат списка топ фильмов ограниченного по количеству count = {}", count);
        return filmStorage.getAllFilms().stream()
                .sorted(new FilmComparator().reversed())
                .limit(count)
                .collect(Collectors.toList());
    }

    private void checkFilmFindById(Long id) {
        if (filmStorage.getFilm(id) == null) {
            throw new NotFoundException("film c id=" + id + " не найден");
        }
    }
}
