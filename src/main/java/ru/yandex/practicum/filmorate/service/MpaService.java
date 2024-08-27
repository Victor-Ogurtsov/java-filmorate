package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.storage.mpa.DbMpaStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MpaService {
    private final DbMpaStorage dbMpaStorage;

    public List<MPA> getMpaList() {
        log.info("Возврат списка mpa");
        return dbMpaStorage.getMpaList();
    }

    public MPA getMpa(Integer id) {
        if (dbMpaStorage.isMpaIdExists(id)) {
            throw new NotFoundException("mpa c id=" + id + " не найден");
        }
        log.info("Возврат mpa c id = {}", id);
        return dbMpaStorage.getMpa(id);
    }
}
