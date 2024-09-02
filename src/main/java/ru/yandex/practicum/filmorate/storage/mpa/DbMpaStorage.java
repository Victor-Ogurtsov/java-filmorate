package ru.yandex.practicum.filmorate.storage.mpa;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.MPA;

import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class DbMpaStorage {

    private final JdbcTemplate jdbc;
    private final MpaRowMapper mpaRowMapper;

    public List<MPA> getMpaList() {
        return jdbc.query("SELECT * FROM mpa", mpaRowMapper);
    }

    public MPA getMpa(Integer id) {
        return jdbc.queryForObject("SELECT ID, MPA_NAME FROM MPA WHERE ID = ?", mpaRowMapper, id);
    }

    public boolean isMpaIdExists(Integer mpaId) {
        return getMpaList().stream().noneMatch(mpa1 -> Objects.equals(mpa1.getId(), mpaId));
    }
}
