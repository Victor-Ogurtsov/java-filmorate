package ru.yandex.practicum.filmorate.storage.user;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Collection;

@Repository
@RequiredArgsConstructor
@Primary
public class DbUserStorage implements UserStorage {
    private final JdbcTemplate jdbc;
    private final UsersResultSetExtractor usersResultSetExtractor;
    private final UserResultSetExtractor userResultSetExtractor;

    @Override
    public Collection<User> getAllUsers() {
        String sql = "SELECT u.ID, u.EMAIL, u.LOGIN, u.USER_NAME, u.BIRTHDAY, f.FRIEND_ID \n" +
                "FROM USERS u \n" +
                "LEFT JOIN FRIENDS f ON u.ID = f.USER_ID";
        return jdbc.query(sql, usersResultSetExtractor);
    }

    @Override
    public User addUser(User user) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO USERS (EMAIL, LOGIN, USER_NAME, BIRTHDAY)" +
                    " VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setObject(1, user.getEmail());
            ps.setObject(2, user.getLogin());
            ps.setObject(3, user.getName());
            ps.setObject(4, user.getBirthday());
            return ps;
        }, keyHolder);

        user.setId(keyHolder.getKeyAs(Long.class));
        return user;
    }

    @Override
    public User getUser(Long userId) {
        String sql = "SELECT u.ID, u.EMAIL, u.LOGIN, u.USER_NAME, u.BIRTHDAY, f.FRIEND_ID \n" +
                "FROM USERS u \n" +
                "LEFT JOIN FRIENDS f ON u.ID = f.USER_ID\n" +
                "WHERE u.ID = ?";
        return jdbc.query(sql, userResultSetExtractor, userId);
    }

    @Override
    public User updateUser(User user) {
        String sql = "UPDATE USERS SET EMAIL = ?, LOGIN = ?, USER_NAME = ?, BIRTHDAY = ? WHERE ID = ?";
        jdbc.update(sql, user.getEmail(), user.getLogin(), user.getName(), user.getBirthday(), user.getId());
        return getUser(user.getId());
    }
}