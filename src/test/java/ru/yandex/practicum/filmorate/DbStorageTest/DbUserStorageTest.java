package ru.yandex.practicum.filmorate.DbStorageTest;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.CollectionUsersResultSetExtractor;
import ru.yandex.practicum.filmorate.storage.user.DbUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserResultSetExtractor;

import java.util.Collection;
import java.util.HashSet;

@JdbcTest
@Import({DbUserStorage.class, UserResultSetExtractor.class, CollectionUsersResultSetExtractor.class})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("DbUserStorage")
public class DbUserStorageTest {
    private final DbUserStorage dbUserStorage;
    public static final Long TEST_USER_ID = 1L;

    static User getTestUser() {
        User user = new User();
        user.setId(TEST_USER_ID);
        user.setName("Виктор");
        user.setEmail("vicyar89@yandex.ru");
        user.setLogin("vicyar89");
        user.setBirthday("1989-03-06");
        user.setFriends(new HashSet<>());
        return user;
    }

    @Test
    @DisplayName("Должен находить пользователя по id")
    public void should_return_user_when_find_by_id() {
        User user = dbUserStorage.getUser(TEST_USER_ID);

        Assertions.assertThat(user).usingRecursiveComparison().ignoringActualNullFields().isEqualTo(getTestUser());
    }

    @Test
    @DisplayName("Должен возвращать список пользователей добавленных из test-data.sql")
    public void should_return_user_list() {
        Collection<User> users = dbUserStorage.getAllUsers();

        Assertions.assertThat(users.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("Должен добавить пользователя в базу и получить его по id")
    public void should_return_added_user() {
        User addedUser = dbUserStorage.addUser(getTestUser());

        Assertions.assertThat(addedUser).usingRecursiveComparison().ignoringActualNullFields()
                .isEqualTo(dbUserStorage.getUser(addedUser.getId()));
    }
}
