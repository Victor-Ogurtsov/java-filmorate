package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NoContentException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.friends.FriendsStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.Instant;
import java.util.Collection;
import java.util.HashSet;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserStorage userStorage;
    private final FriendsStorage friendsStorage;

    public Collection<User> getAllUsers() {
        log.info("Возврат списка пользователей на эндпоинт GET /users");
        return userStorage.getAllUsers();
    }

    public User addUser(User user) {
        checkUser(user);
        User addedUser = userStorage.addUser(user);
        log.info("Добавлен пользователь с id={}", user.getId());
        return addedUser;
    }

    public User updateUser(User user) {
        checkUser(user);
        if (user.getId() == null) {
            throw new ValidationException("Id должен быть указан");
        }
        checkUserFindById(user.getId());
        log.info("Изменен пользователь с id={}", user.getId());
        return userStorage.updateUser(user);
    }

    public User getUser(Long userId) {
        //checkUserFindById(userId);
        log.info("Возврат пользователя с id={}", userId);
        return userStorage.getUser(userId);
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
        log.info("Пользователь с id {} добавился в друзья пользователю с id {}", friendId, id);
        friendsStorage.addFriend(id, friendId);
    }

    public void deleteFriend(Long id, Long friendId) {
        checkUserFindById(id);
        checkUserFindById(friendId);
        if (!friendsStorage.getFriends(id).contains(friendId)) {
            throw new NoContentException("Пользователя с id = " + friendId + " нет в друзьях у пользователя с id " + id);
        }
        log.info("Пользователь с id = {} больше не друг пользователю с id = {}", id, friendId);
        friendsStorage.deleteFriend(id, friendId);
    }

    public Collection<User> getFriends(Long id) {
        checkUserFindById(id);
        Collection<Long> idsFriends = friendsStorage.getFriends(id);
        log.info("Возврат списка друзей пользователя с id = {}", id);
        return idsFriends.stream().map(id1 -> userStorage.getUser(id1)).toList();
    }

    public Collection<User> getMutualFriends(Long id, Long otherId) {
        checkUserFindById(id);
        checkUserFindById(otherId);
        log.info("Возврат списка общих друзей пользователей с id {} и {}", id, otherId);
        return friendsStorage.getFriends(id).stream()
                .filter(id1 -> friendsStorage.getFriends(otherId).contains(id1))
                .map(id1 -> userStorage.getUser(id1)).toList();
    }

    protected void checkUserFindById(Long id) {
        if (userStorage.getUser(id) == null) {
            throw new NotFoundException("Пользователь c id=" + id + " не найден");
        }
    }
}
