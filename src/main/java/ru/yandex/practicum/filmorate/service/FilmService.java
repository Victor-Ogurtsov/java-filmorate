package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FilmComparator;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.time.Instant;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FilmService {

    private final FilmStorage inMemoryFilmStorage;
    private final UserService userService;

    public Collection<Film> getAllFilms() {
        log.info("Возврат списка фильмов");
        return inMemoryFilmStorage.getAllFilms();
    }

    public Film addFilm(Film film) {
        checkFilm(film);
        film.setId(inMemoryFilmStorage.getNextId());
        log.info("Добавлен фильм с id={}", film.getId());
        return inMemoryFilmStorage.addFilm(film);
    }

    public Film updateFilm(Film film) {
        checkFilm(film);
        if (film.getId() == null) {
            throw new ValidationException("Id должен быть указан");
        }
        checkFilmFindById(film.getId());
        log.info("Изменен фильм с id={}", film.getId());
        return inMemoryFilmStorage.addFilm(film);
    }

    public Film getFilm(Long filmId) {
        log.info("Возврат фильма с id={}", filmId);
        checkFilmFindById(filmId);
        return inMemoryFilmStorage.getFilm(filmId);
    }

    public void checkFilm(Film film) {
        if (film == null) {
            throw new ValidationException("film equal null");
        } else if (film.getDescription().length() > 200) {
            throw new ValidationException("Максимальная длина описания — 200 символов");
        } else if (Instant.parse(film.getReleaseDate() + "T00:00:00Z").isBefore(Instant.parse("1895-12-28T00:00:00Z"))) {
            throw new ValidationException("Дата релиза — не раньше 28 декабря 1895 года");
        }
        if (film.getIdsUsersWhoLiked() == null) {
            film.setIdsUsersWhoLiked(new HashSet<>());
        }
    }

    public void likeIt(Long id, Long userId) {
        checkFilmFindById(id);
        userService.checkUserFindById(userId);
        inMemoryFilmStorage.getFilm(id).getIdsUsersWhoLiked().add(userId);
        log.info("Фильм с id = {} получил лайк от пользователя с id = {}", id, userId);
    }

    public void deleteLike(Long id, Long userId) {
        checkFilmFindById(id);
        userService.checkUserFindById(userId);
        inMemoryFilmStorage.getFilm(id).getIdsUsersWhoLiked().remove(userId);
        log.info("У фильма с id = {}  удален лайк от пользователя с id = {}", id, userId);
    }

    public Collection<Film> getTopFilms(Long count) {
        log.info("Возврат списка топ фильмов ограниченного по количеству count = {}", count);
        return inMemoryFilmStorage.getAllFilms().stream()
                .sorted(new FilmComparator().reversed())
                .limit(count)
                .collect(Collectors.toList());
    }

    private void checkFilmFindById(Long id) {
        if (!inMemoryFilmStorage.idIsPresent(id)) {
            throw new NotFoundException("film c id=" + id + " не найден");
        }
    }
}
