package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Film.
 */
@Data
@AllArgsConstructor
public class Film {
    Long id;
    String name;
    String description;
    String releaseDate;
    Integer duration;
}
