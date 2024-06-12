package ru.yandex.practicum.filmorate.controllerTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

@SpringBootTest
public class UserControllerTest {

    UserController userController;

    @BeforeEach
    void createUserController() {
        userController = new UserController(new UserService(new InMemoryUserStorage()));
    }

    @Test
    void shouldReturn2ThenAdd2Users() {
        userController.addUser(new User(null, "1@email", "login1", "name1", "1895-12-28", null));
        userController.addUser(new User(null, "2@email", "login2", "name2", "1995-12-28", null));

        Assertions.assertEquals(2, userController.getAllUsers().size(), "количество пользователей" +
                " в приложении не соответствует количеству добавленных пользователей");
    }

    @Test
    void shouldThrowValidationExceptionThenAddNewUserAndUserIsNull() {
        Assertions.assertThrows(ValidationException.class, () -> userController.addUser(null), "Не выброшено исключение" +
                "при user равном null");
    }

    @Test
    void shouldThrowValidationExceptionThenUpdateUserAndUserIsNull() {
        Assertions.assertThrows(ValidationException.class, () -> userController.updateUser(null), "Не выброшено исключение" +
                "при user равном null");
    }

    @Test
    void shouldThrowValidationExceptionThenAddNewUserAndBirthdayAfterNow() {
        User user = new User(null, "email@", "   ", "name", "2095-12-28", null);

        Assertions.assertThrows(ValidationException.class, () -> userController.addUser(user), "Не выброшено исключение" +
                "при при дате дня рождения позже текущей");
    }

    @Test
    void shouldThrowValidationExceptionThenUpdateUserAndBirthdayAfterNow() {
        User user = userController.addUser(new User(null, "email@", "login", "name", "1895-12-28", null));
        user.setBirthday("2095-12-28");

        Assertions.assertThrows(ValidationException.class, () -> userController.updateUser(user), "Не выброшено исключение" +
                "при при дате дня рождения позже текущей");
    }

    @Test
    void shouldEqualThenNameIsNull() {
        User user = userController.addUser(new User(null, "email@", "login", null, "1895-12-28", null));

        Assertions.assertEquals(user.getLogin(), user.getName(), "имя не эквивалентно логину");
    }
}
