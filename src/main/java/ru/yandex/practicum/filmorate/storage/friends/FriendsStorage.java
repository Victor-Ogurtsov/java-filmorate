package ru.yandex.practicum.filmorate.storage.friends;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
@RequiredArgsConstructor
public class FriendsStorage {
    private final JdbcTemplate jdbc;

    public void addFriend(Long id, Long friendId) {
        String sql = "INSERT INTO FRIENDS (USER_ID, FRIEND_ID) VALUES (?, ?)";

        jdbc.update(sql, id, friendId);
    }

    public void deleteFriend(Long id, Long friendId) {
        String sql = "DELETE FRIENDS WHERE USER_ID = ? AND FRIEND_ID = ?";

        jdbc.update(sql, id, friendId);
    }

    public Collection<Long> getFriends(Long id) {
        String sql = "SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID = ?";

        return jdbc.queryForList(sql, Long.class, id);
    }
}
