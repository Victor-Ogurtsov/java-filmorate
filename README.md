# java-filmorate
Template repository for Filmorate project.

![ER diagram](/ER-diagram-filmorate.png)

--Получить все фильмы
SELECT "name" FROM films f;

--Добавить фильм
INSERT INTO filmorate.films (id, "name", description, releasedate, duration, genre_id, rating_id) 
VALUES(5, 'Тюлени', 'Описание фильма Тюлени....', '2014-09-06', 65, 1, 5);

--Обновить фильм
UPDATE filmorate.films
SET "name"='Схватка'
WHERE id=1;

--Получить фильм по id
SELECT "name" 
FROM films f 
WHERE id = 2;

--Поставить лайк фильму
INSERT INTO filmorate.likes (film_id , user_id) 
VALUES(3, 4);

--Удалить лайк у фильма
DELETE FROM filmorate.likes
WHERE film_id=1 AND user_id=3;

--Получить список из 10 фильмов с наибольшим количеством лайков
SELECT f."name",
       COUNT(l.user_id)
FROM films f
JOIN likes l ON f.id = l.film_id
GROUP BY f."name"
LIMIT 10;

--Получить всех пользователей
SELECT *
FROM users;

--Добавить пользователя
INSERT INTO filmorate.users (id, email, login, "name", birthday) VALUES(1, 'nikol@mail.ru', 'nikola', 'Николай', '1996-03-07');

--Изменить информацию о пользователе
UPDATE filmorate.users
SET email='nikol@mail.ru'
WHERE id=1;

--Получить пользователя по id
SELECT *
FROM users u
WHERE id=1;

--Добавить друга
INSERT INTO filmorate.friends (initiator_friendship_id, possible_friend_id, friendship_status_id) VALUES(1, 2, 1);

--Получить список подтвержденных друзей
SELECT *
FROM users u
JOIN friends f ON u.id = f.initiator_friendship_id OR u.id = f.possible_friend_id
WHERE u.id = 2 AND f.friendship_status_id = 1;

--Удалить из друзей
DELETE FROM filmorate.friends
WHERE initiator_friendship_id=1 AND possible_friend_id=3;