package ru.yandex.practicum.filmorate.controllerTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.ValidationException;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.model.User;

@SpringBootTest
public class UserControllerTest {

    UserController userController;

    @BeforeEach
    void createUserController() {
        userController = new UserController();
    }

    @Test
    void shouldReturn2ThenAdd2Users() {
        userController.addUser(new User(null, "1@email", "login1", "name1", "1895-12-28"));
        userController.addUser(new User(null, "2@email", "login2", "name2", "1995-12-28"));

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
    void shouldThrowValidationExceptionThenAddNewUserAndEmailDoesNotContainDog() {
        User user = new User(null, "email", "login1", "name1", "1895-12-28");

        Assertions.assertThrows(ValidationException.class, () -> userController.addUser(user), "Не выброшено исключение" +
                "при отсутствии @ в почте");
    }

    @Test
    void shouldThrowValidationExceptionThenAddNewUserAndLoginEqualNull() {
        User user = new User(null, "email@", null, "name", "1895-12-28");

        Assertions.assertThrows(ValidationException.class, () -> userController.addUser(user), "Не выброшено исключение" +
                "при отсутствии значения в логине");
    }

    @Test
    void shouldThrowValidationExceptionThenAddNewUserAndLoginContainSpace() {
        User user = new User(null, "email@", "login 1", "name", "1895-12-28");

        Assertions.assertThrows(ValidationException.class, () -> userController.addUser(user), "Не выброшено исключение" +
                "при наличии пробела в логине");
    }

    @Test
    void shouldThrowValidationExceptionThenAddNewUserAndLoginConsistSpace() {
        User user = new User(null, "email@", "   ", "name", "1895-12-28");

        Assertions.assertThrows(ValidationException.class, () -> userController.addUser(user), "Не выброшено исключение" +
                "при логине состоящем из пробелов");
    }

    @Test
    void shouldThrowValidationExceptionThenAddNewUserAndBirthdayAfterNow() {
        User user = new User(null, "email@", "   ", "name", "2095-12-28");

        Assertions.assertThrows(ValidationException.class, () -> userController.addUser(user), "Не выброшено исключение" +
                "при при дате дня рождения позже текущей");
    }

    @Test
    void shouldThrowValidationExceptionThenUpdateUserAndEmailDoesNotContainDog() {
        User user = userController.addUser(new User(null, "email@", "login", "name", "1895-12-28"));
        user.setEmail("NewEmail");

        Assertions.assertThrows(ValidationException.class, () -> userController.updateUser(user), "Не выброшено исключение" +
                "при отсутствии @ в почте");
    }

    @Test
    void shouldThrowValidationExceptionThenUpdateUserAndLoginEqualNull() {
        User user = userController.addUser(new User(null, "email@", "login", "name", "1895-12-28"));
        user.setLogin(null);

        Assertions.assertThrows(ValidationException.class, () -> userController.updateUser(user), "Не выброшено исключение" +
                "при отсутствии значения в логине");
    }

    @Test
    void shouldThrowValidationExceptionUpdateUserAndLoginContainSpace() {
        User user = userController.addUser(new User(null, "email@", "login", "name", "1895-12-28"));
        user.setLogin("login 1");

        Assertions.assertThrows(ValidationException.class, () -> userController.updateUser(user), "Не выброшено исключение" +
                "при наличии пробела в логине");
    }

    @Test
    void shouldThrowValidationExceptionThenUpdateUserAndLoginConsistSpace() {
        User user = userController.addUser(new User(null, "email@", "login", "name", "1895-12-28"));
        user.setLogin("   ");

        Assertions.assertThrows(ValidationException.class, () -> userController.updateUser(user), "Не выброшено исключение" +
                "при логине состоящем из пробелов");
    }

    @Test
    void shouldThrowValidationExceptionThenUpdateUserAndBirthdayAfterNow() {
        User user = userController.addUser(new User(null, "email@", "login", "name", "1895-12-28"));
        user.setBirthday("2095-12-28");

        Assertions.assertThrows(ValidationException.class, () -> userController.updateUser(user), "Не выброшено исключение" +
                "при при дате дня рождения позже текущей");
    }

    @Test
    void shouldEqualThenNameIsNull() {
        User user = userController.addUser(new User(null, "email@", "login", null, "1895-12-28"));

        Assertions.assertEquals(user.getLogin(), user.getName(), "имя не эквивалентно логину");
    }
}
