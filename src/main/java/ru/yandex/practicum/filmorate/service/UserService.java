package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.Instant;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserStorage inMemoryUserStorage;

    public Collection<User> getAllUsers() {
        log.info("Возврат списка пользователей на эндпоинт GET /users");
        return inMemoryUserStorage.getAllUsers();
    }

    public User addUser(User user) {
        checkUser(user);
        user.setId(inMemoryUserStorage.getNextId());
        log.info("Добавлен пользователь с id={}", user.getId());
        return inMemoryUserStorage.addUser(user);
    }

    public User updateUser(User user) {
        checkUser(user);
        if (user.getId() == null) {
            throw new ValidationException("Id должен быть указан");
        }
        checkUserFindById(user.getId());
        log.info("Изменен пользователь с id={}", user.getId());
        return inMemoryUserStorage.addUser(user);
    }

    public User getUser(Long userId) {
        checkUserFindById(userId);
        log.info("Возврат пользователя с id={}", userId);
        return inMemoryUserStorage.getUser(userId);
    }

    private void checkUser(User user) {
        if (user == null) {
            throw new ValidationException("user equal null");
        } else if (Instant.parse(user.getBirthday() + "T00:00:00Z").isAfter(Instant.now())) {
            throw new ValidationException("Дата рождения не может быть в будущем");
        }
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
        if (user.getFriends() == null) {
            user.setFriends(new HashSet<>());
        }
    }

    public void addFriend(Long id, Long friendId) {
        checkUserFindById(id);
        checkUserFindById(friendId);
        inMemoryUserStorage.getUser(id).getFriends().add(friendId);
        inMemoryUserStorage.getUser(friendId).getFriends().add(id);
        log.info("Пользователи с id {} и {} добавлены в друзья", id, friendId);
    }

    public void deleteFriend(Long id, Long friendId) {
        checkUserFindById(id);
        checkUserFindById(friendId);
        inMemoryUserStorage.getUser(id).getFriends().remove(friendId);
        inMemoryUserStorage.getUser(friendId).getFriends().remove(id);
        log.info("Пользователи с id {} и {} больше не друзья", id, friendId);
    }

    public Collection<User> getFriends(Long id) {
        checkUserFindById(id);
        Set<Long> idsFriends = inMemoryUserStorage.getUser(id).getFriends();
        log.info("Возврат списка друзей пользователя с id = {}", id);
        return inMemoryUserStorage.getAllUsers()
                .stream()
                .filter(user -> idsFriends.contains(user.getId()))
                .collect(Collectors.toList());
    }

    public Collection<User> getMutualFriends(Long id, Long otherId) {
        checkUserFindById(id);
        checkUserFindById(otherId);
        log.info("Возврат списка общих друзей пользователей с id {} и {}", id, otherId);
        return inMemoryUserStorage.getAllUsers().stream()
                .filter(user -> inMemoryUserStorage.getUser(id).getFriends().contains(user.getId()))
                .filter(user -> inMemoryUserStorage.getUser(otherId).getFriends().contains(user.getId()))
                .collect(Collectors.toList());
    }

    protected void checkUserFindById(Long id) {
        if (!inMemoryUserStorage.idIsPresent(id)) {
            throw new NotFoundException("Пользователь c id=" + id + " не найден");
        }
    }
}
