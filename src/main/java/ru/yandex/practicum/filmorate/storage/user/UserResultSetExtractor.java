package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

@Component
public class UserResultSetExtractor implements ResultSetExtractor<User> {
    @Override
    public User extractData(ResultSet rs) throws SQLException, DataAccessException {
        User user = null;
        while (rs.next()) {
            Long friendId = rs.getLong("FRIEND_ID");

            if (user == null) {
                user = new User();
                user.setId(rs.getLong("ID"));
                user.setEmail(rs.getString("EMAIL"));
                user.setLogin(rs.getString("LOGIN"));
                user.setName(rs.getString("USER_NAME"));
                user.setBirthday(rs.getString("BIRTHDAY"));
                user.setFriends(new HashSet<>());
                if (friendId > 0) {
                    user.getFriends().add(friendId);
                }
            } else {
                user.getFriends().add(friendId);
            }
        }
        return user;
    }
}
