# java-filmorate
Template repository for Filmorate project.

### ER diagram
![ER diagram](/ER-diagram-filmorate.png)
<br>

#### Таблица <u>films</u> содержит информацию о фильмах. PK - id; FK - rating_id.

Запрос на получение списка фильмов со всеми данными
``` sql
SELECT f.ID, f.FILM_NAME, f.DESCRIPTION, f.RELEASEDATE, f.DURATION, f.MPA, m.MPA_NAME, fg.GENRE_ID, gn.GENRE_NAME, l.USER_ID 
FROM FILMS f
LEFT JOIN FILMS_GENRES fg ON f.ID = fg.FILM_ID 
LEFT JOIN GENRE_NAMES gn ON gn.ID = fg.GENRE_ID 
LEFT JOIN MPA m ON f.MPA = m.ID
LEFT JOIN LIKES l ON f.ID = l.FILM_ID;
```
<br>

Запрос на добавление фильма
``` sql
INSERT INTO FILMS (FILM_NAME, DESCRIPTION, RELEASEDATE DURATION, MPA) VALUES ('Название', 'Описание ', '2014-09-06', 65, 3);
```
<br>

Запрос на обновление информации 
```sql
UPDATE FILMS SET FILM_NAME = 'Новое название',
DESCRIPTION = 'Новое описание',
RELEASEDATE = '2000-10-11',
DURATION = 130,
MPA = 2 
WHERE ID = 2;
```
<br>

Запрос фильма по id
``` sql
SELECT f.ID, f.FILM_NAME, f.DESCRIPTION, f.RELEASEDATE, f.DURATION, f.MPA, m.MPA_NAME, fg.GENRE_ID, gn.GENRE_NAME, l.USER_ID 
FROM FILMS f
LEFT JOIN FILMS_GENRES fg ON f.ID = fg.FILM_ID 
LEFT JOIN GENRE_NAMES gn ON gn.ID = fg.GENRE_ID 
LEFT JOIN MPA m ON f.MPA = m.ID
LEFT JOIN LIKES l ON f.ID = l.FILM_ID
WHERE f.ID = 2;
```
<br>

#### Таблица <u>ratings</u> содержит названия рейтингов фильмов. PK - id.

Запрос названия рейтинга фильма по id фильма
``` sql
SELECT ID, MPA_NAME FROM MPA WHERE ID = 3;
```
<br>

#### Таблица <u>genre_names</u> содержит названия жанров фильмов. PK - id.
#### Таблица <u>films_genres</u> содержит соответствия идентификаторов фильмов и идентификаторов жанров. PK - (film_id, genre_id);  FK - film_id; FK - genre_id;

Запрос жанра фильма по id
``` sql
SELECT * FROM GENRE_NAMES WHERE ID = 5;
```
<br>

#### Таблица <u>users</u> содержит информацию о пользователях. PK - (id).

Запрос на получение списка пользователей со всеми данными
``` sql
SELECT u.ID, u.EMAIL, u.LOGIN, u.USER_NAME, u.BIRTHDAY, f.FRIEND_ID 
FROM USERS u 
LEFT JOIN FRIENDS f ON u.ID = f.USER_ID;
```
<br>

Запрос на добавление пользователя
``` sql
INSERT INTO USERS (EMAIL, LOGIN, USER_NAME, BIRTHDAY) 
VALUES('nikol@mail.ru', 'nikola', 'Николай', '1996-03-07');
```
<br>

Запрос на обновление информации
```sql
UPDATE USERS SET EMAIL = 'gjxn@sdfs',
LOGIN = 'login',
USER_NAME = 'Новое имя',
BIRTHDAY = '1995-08-04'
WHERE ID = 1;
```
<br>

#### Таблица <u>likes</u> содержит соответствия идентификаторов фильмов и идентификаторов пользователей. PK - (film_id, user_id);  FK - film_id; FK - user_id;
#### Таблица <u>friends</u> содержит соответствия идентификаторов пользователя и его друга. PK - (user_id, friend_id);  FK - user_id; FK - friend_id; Поле friendship_confirmed содержит значение 'yes', если у обоих пользователей присутствуют записи с их идентификатором в колонке friend_id, в противном случае 'no'.

Запрос на добавление одним пользователем себе в друзья другого пользователя
``` sql
INSERT INTO FRIENDS (USER_ID, FRIEND_ID) VALUES (1, 2);
```
<br>