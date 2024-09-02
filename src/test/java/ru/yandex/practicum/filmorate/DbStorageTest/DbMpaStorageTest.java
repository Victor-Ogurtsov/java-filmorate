package ru.yandex.practicum.filmorate.DbStorageTest;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.storage.mpa.DbMpaStorage;
import ru.yandex.practicum.filmorate.storage.mpa.MpaRowMapper;

import java.util.List;

@JdbcTest
@Import({DbMpaStorage.class, MpaRowMapper.class})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("DbMpaStorage")
public class DbMpaStorageTest {
    private final DbMpaStorage dbMpaStorage;

    @Test
    @DisplayName("Должен вернуть список рейтингов(mpa) добавленных из test-data.sql")
    public void should_return_mpa_list() {
        List<MPA> mpaList = dbMpaStorage.getMpaList();

        Assertions.assertThat(mpaList.size()).usingRecursiveComparison().ignoringActualNullFields().isEqualTo(5);
    }

    @Test
    @DisplayName("Должен вернуть mpa по id")
    public void should_return_mpa_by_id() {
        MPA mpa = new MPA(1, "G");

        Assertions.assertThat(dbMpaStorage.getMpa(1)).usingRecursiveComparison()
                .ignoringActualNullFields().isEqualTo(mpa);
    }
}
