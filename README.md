# java-filmorate
Template repository for Filmorate project.

### ER diagram
![ER diagram](/ER-diagram-filmorate.png)
<br>

#### Таблица <u>films</u> содержит информацию о фильмах. PK - id; FK - rating_id.

Запрос на получение списка фильмов со всеми данными
``` sql
SELECT *
FROM films;
```
<br>

Запрос на добавление фильма
``` sql
INSERT INTO films (name, description, release_date, duration, rating_id) 
VALUES('Название', 'Описание ', '2014-09-06', 65, 3);
```
<br>

Запрос на обновление информации 
```sql
UPDATE films
SET name = 'Новое название'
WHERE id = 1;
```
<br>

Запрос фильма по id
``` sql
SELECT name
FROM films 
WHERE id = 2;
```
<br>

#### Таблица <u>ratings</u> содержит названия рейтингов фильмов. PK - id.

Запрос названия рейтинга фильма по id фильма
``` sql
SELECT name
FROM ratings 
WHERE id = (
    SELECT rating_id
    FROM films
    WHERE id = 1);
```
<br>

#### Таблица <u>genre_names</u> содержит названия жанров фильмов. PK - id.
#### Таблица <u>films_genres</u> содержит соответствия идентификаторов фильмов и идентификаторов жанров. PK - (film_id, genre_id);  FK - film_id; FK - genre_id;

Запрос списка фильмов в жанре 'Комедия'
``` sql
SELECT *
FROM films 
WHERE id IN (
    SELECT film_id
    FROM films_genres fg 
    JOIN genre_names gn ON fg.genre_id = gn.id
    WHERE gn.name = 'Комедия' 
);
```
<br>


#### Таблица <u>users</u> содержит информацию о пользователях. PK - (id).

Запрос на получение списка пользователей со всеми данными
``` sql
SELECT *
FROM users;
```
<br>

Запрос на добавление пользователя
``` sql
INSERT INTO users (email, login, name, birthday) 
VALUES('nikol@mail.ru', 'nikola', 'Николай', '1996-03-07');
```
<br>

Запрос на обновление информации
```sql
UPDATE users
SET name = 'Новое имя'
WHERE id = 1;
```
<br>

Запрос почты пользователя по id
``` sql
SELECT email
FROM users 
WHERE id = 2;
```
<br>

#### Таблица <u>likes</u> содержит соответствия идентификаторов фильмов и идентификаторов пользователей. PK - (film_id, user_id);  FK - film_id; FK - user_id;

Зарос таблицы топ 10 фильмов по количеству лайков с названиями фильмов и количеством лайков
``` sql
SELECT f.name AS film_name,
       count_likes
FROM (
   SELECT l.film_id,
          count(l.user_id) as count_likes
   FROM likes l
   GROUP BY l.film_id
) AS group_likes_by_films
INNER JOIN films f ON group_likes_by_films.film_id = f.id
ORDER BY count_likes DESC
LIMIT 10;
```
<br>

#### Таблица <u>friends</u> содержит соответствия идентификаторов пользователя и его друга. PK - (user_id, friend_id);  FK - user_id; FK - friend_id; Поле friendship_confirmed содержит значение 'yes', если у обоих пользователей присутствуют записи с их идентификатором в колонке friend_id, в противном случае 'no'.

Запрос на добавление одним пользователем (id=1) себе в друзья другого пользователя (id=2).
``` sql
IF EXISTS (
    SELECT *
    FROM friends
    WHERE user_id = 2 AND friend_id = 1
) BEGIN 

INSERT INTO friends (user_id, friend_id, friendship_confirmed)
VALUES(1, 2, 'yes');
UPDATE friends
SET friendship_confirmed = 'yes'
WHERE user_id = 2 AND friend_id = 1

END ELSE BEGIN
INSERT INTO friends (user_id, friend_id, friendship_confirmed)
VALUES(1, 2, 'no')
```
<br>

Запрос списка подтвержденных пользователей, которые являются друзями пользователя с id=1
``` sql
SELECT *
FROM users 
WHERE id IN (
    SELECT friend_id
    FROM friends
    WHERE user_id = 1 AND friendship_confirmed = 'yes'
);
```
<br>