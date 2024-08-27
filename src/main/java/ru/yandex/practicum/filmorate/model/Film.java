package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Film {
    private Long id;
    @NotBlank
    private String name;
    private String description;
    private String releaseDate;
    @Positive
    private Integer duration;
    private Set<Long> idsUsersWhoLiked;
    private MPA mpa;
    private LinkedHashSet<Genre> genres;
}
