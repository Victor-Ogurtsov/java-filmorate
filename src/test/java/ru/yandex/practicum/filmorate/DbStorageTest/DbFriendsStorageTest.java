package ru.yandex.practicum.filmorate.DbStorageTest;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.storage.friends.DbFriendsStorage;
import java.util.Collection;

@JdbcTest
@Import(DbFriendsStorage.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("DbFriendsStorage")
public class DbFriendsStorageTest {
    private final DbFriendsStorage dbFriendsStorage;

    @Test
    @DisplayName("Должен вернуть список id друзей добавленных из test-data.sql для пользователя с id=3")
    public void should_return_friendId_list_by_id() {
        Collection<Long> friendIdList = dbFriendsStorage.getFriends(3L);

        Assertions.assertThat(friendIdList.size()).usingRecursiveComparison().ignoringActualNullFields().isEqualTo(2);
    }
}
