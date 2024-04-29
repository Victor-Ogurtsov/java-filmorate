package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController extends BaseController<User> {

    private final Map<Long, User> users = new HashMap<>();

    @GetMapping
    public Collection<User> getAllUsers() {
        log.info("Возврат списка пользователей на эндпоинт GET /users");
        return users.values();
    }

    @PostMapping
    public User addUser(@RequestBody User user) {
        checkUser(user);
        user.setId(getNextId(users));
        log.info("Добавлен пользователь с id={}", user.getId());
        users.put(user.getId(), user);
        return users.get(user.getId());
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        checkUser(user);
        if (user.getId() == null) {
            throw new ValidationException("Id должен быть указан");
        } else if (!users.containsKey(user.getId())) {
            throw new ValidationException("user c id=" + user.getId() + " не найден");
        }
        log.info("Изменен пользователь с id={}", user.getId());
        users.put(user.getId(), user);
        return users.get(user.getId());
    }

    private void checkUser(User user){
        if (user == null) {
            throw new ValidationException("user equal null");
        } else if (!user.getEmail().contains("@")){
            throw new ValidationException("Электронная почта не может быть пустой и должна содержать символ @");
        } else if (user.getLogin() == null || user.getLogin().isEmpty() || user.getLogin().trim().contains(" ") || user.getLogin().isBlank()) {
            throw new ValidationException("Логин не может быть пустым и содержать пробел");
        }  else if (Instant.parse(user.getBirthday() + "T00:00:00Z").isAfter(Instant.now())){
            throw new ValidationException("Дата рождения не может быть в будущем");
        }
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
    }




}
