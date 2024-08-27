package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genres.DbGenresStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GenreService {
    private final DbGenresStorage dbGenresStorage;

    public List<Genre> getGenres() {
        log.info("Возврат списка жанров");
        return dbGenresStorage.getGenresList();
    }

    public Genre getGenre(Integer id) {
        if (!dbGenresStorage.isGenreIdExists(id)) {
            throw new NotFoundException("жанр с id = " + id + "не найден");
        }
        log.info("Возврат жанра с id = {}", id);
        return dbGenresStorage.getGenre(id);
    }
}
