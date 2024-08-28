package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NoContentException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.friends.DbFriendsStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.Instant;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserStorage userStorage;
    private final DbFriendsStorage dbFriendsStorage;

    public Collection<User> getAllUsers() {
        log.info("Возврат списка пользователей на эндпоинт GET /users");
        return userStorage.getAllUsers();
    }

    public User addUser(User user) {
        checkUser(user);
        User addedUser = userStorage.addUser(user);
        Long userId = addedUser.getId();
        Set<Long> friendsIds = user.getFriends();
        log.info("Добавлен пользователь с id={}", userId);
        if (friendsIds != null) {
            for (Long friendId : friendsIds) {
                dbFriendsStorage.addFriend(userId, friendId);
            }
            log.info("Для пользователя с id={} добавлены друзья с id: {}", userId, friendsIds);
        }
        return userStorage.getUser(userId);
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
        dbFriendsStorage.addFriend(id, friendId);
    }

    public void deleteFriend(Long id, Long friendId) {
        checkUserFindById(id);
        checkUserFindById(friendId);
        if (!dbFriendsStorage.getFriends(id).contains(friendId)) {
            throw new NoContentException("Пользователя с id = " + friendId + " нет в друзьях у пользователя с id " + id);
        }
        log.info("Пользователь с id = {} больше не друг пользователю с id = {}", id, friendId);
        dbFriendsStorage.deleteFriend(id, friendId);
    }

    public Collection<User> getFriends(Long id) {
        checkUserFindById(id);
        Collection<Long> idsFriends = dbFriendsStorage.getFriends(id);
        log.info("Возврат списка друзей пользователя с id = {}", id);
        return idsFriends.stream().map(id1 -> userStorage.getUser(id1)).toList();
    }

    public Collection<User> getMutualFriends(Long id, Long otherId) {
        checkUserFindById(id);
        checkUserFindById(otherId);
        log.info("Возврат списка общих друзей пользователей с id {} и {}", id, otherId);
        return dbFriendsStorage.getFriends(id).stream()
                .filter(id1 -> dbFriendsStorage.getFriends(otherId).contains(id1))
                .map(id1 -> userStorage.getUser(id1)).toList();
    }

    protected void checkUserFindById(Long id) {
        if (userStorage.getUser(id) == null) {
            throw new NotFoundException("Пользователь c id=" + id + " не найден");
        }
    }
}
