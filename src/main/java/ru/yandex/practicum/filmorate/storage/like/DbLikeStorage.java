package ru.yandex.practicum.filmorate.storage.like;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DbLikeStorage {
    private final JdbcTemplate jdbc;

    public void likeIt(Long id, Long userId) {
        String sql = "INSERT INTO LIKES (FILM_ID, USER_ID) VALUES (?, ?)";

        jdbc.update(sql, id, userId);
    }

    public void deleteLike(Long id, Long userId) {
        String sql = "DELETE LIKES WHERE FILM_ID = ? AND USER_ID = ?";

        jdbc.update(sql, id, userId);
    }
}
