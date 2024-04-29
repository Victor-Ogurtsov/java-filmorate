package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class User {
    Long id;
    String email;
    String login;
    String name;
    String birthday;
}