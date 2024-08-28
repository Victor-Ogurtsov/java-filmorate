MERGE INTO genre_names (id, genre_name) values(1, 'Комедия');
MERGE INTO genre_names (id, genre_name) values(2, 'Драма');
MERGE INTO genre_names (id, genre_name) values(3, 'Мультфильм');
MERGE INTO genre_names (id, genre_name) values(4, 'Триллер');
MERGE INTO genre_names (id, genre_name) values(5, 'Документальный');
MERGE INTO genre_names (id, genre_name) values(6, 'Боевик');

MERGE INTO mpa (id, mpa_name) VALUES (1, 'G');
MERGE INTO mpa (id, mpa_name) VALUES (2, 'PG');
MERGE INTO mpa (id, mpa_name) VALUES (3, 'PG-13');
MERGE INTO mpa (id, mpa_name) VALUES (4, 'R');
MERGE INTO mpa (id, mpa_name) VALUES (5, 'NC-17');

INSERT INTO FILMS (FILM_NAME, DESCRIPTION, RELEASEDATE, DURATION, MPA)
VALUES ('Фильм', 'Описание', '2015-10-11', 130, 2);
INSERT INTO FILMS (FILM_NAME, DESCRIPTION, RELEASEDATE, DURATION, MPA)
VALUES ('Фильм2', 'Описание2', '2015-10-15', 140, 3);

INSERT INTO FILMS_GENRES (film_id, genre_id) VALUES (1, 3);

INSERT INTO USERS (EMAIL, LOGIN, USER_NAME, BIRTHDAY) VALUES ('vicyar89@yandex.ru', 'vicyar89', 'Виктор', '1989-03-06');
INSERT INTO USERS (EMAIL, LOGIN, USER_NAME, BIRTHDAY) VALUES ('sdfsdf@yandex.ru', 'fsdf67', 'Никита', '1984-06-02');
INSERT INTO USERS (EMAIL, LOGIN, USER_NAME, BIRTHDAY) VALUES ('fdfs@yandex.ru', '4354g7', 'федор', '1994-06-02');


INSERT INTO LIKES (FILM_ID, USER_ID) VALUES (1, 1);

INSERT INTO FRIENDS (USER_ID, FRIEND_ID) VALUES (3, 1);
INSERT INTO FRIENDS (USER_ID, FRIEND_ID) VALUES (3, 2);