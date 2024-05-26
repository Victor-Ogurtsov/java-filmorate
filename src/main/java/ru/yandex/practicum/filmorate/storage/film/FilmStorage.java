package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {
    Collection<Film> getAllFilms();

    long getNextId();

    Film addFilm(Film film);

    boolean idIsPresent(Long id);

    Film getFilm(Long filmId);
}