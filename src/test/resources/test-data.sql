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