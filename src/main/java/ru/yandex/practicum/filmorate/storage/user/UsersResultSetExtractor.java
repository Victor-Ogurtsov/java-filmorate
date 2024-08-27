package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

@Component
public class UsersResultSetExtractor implements ResultSetExtractor<Collection<User>> {
    @Override
    public Collection<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
        HashMap<Long, User> users = new HashMap<>();
        while (rs.next()) {
            Long id = rs.getLong("ID");
            Long friendId = rs.getLong("FRIEND_ID");

            if (users.get(id) == null) {
                User user = new User();
                user.setId(id);
                user.setEmail(rs.getString("EMAIL"));
                user.setLogin(rs.getString("LOGIN"));
                user.setName(rs.getString("USER_NAME"));
                user.setBirthday(rs.getString("BIRTHDAY"));
                user.setFriends(new HashSet<>());
                if (friendId > 0) {
                    user.getFriends().add(friendId);
                }
                users.put(id, user);
            } else {
                users.get(id).getFriends().add(friendId);
            }
        }
        return users.values();
    }
}
