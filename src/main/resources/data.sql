MERGE INTO genre_names (id, "name") values(1, 'Комедия');
MERGE INTO genre_names (id, "name") values(2, 'Драма');
MERGE INTO genre_names (id, "name") values(3, 'Мультфильм');
MERGE INTO genre_names (id, "name") values(4, 'Триллер');
MERGE INTO genre_names (id, "name") values(5, 'Документальный');
MERGE INTO genre_names (id, "name") values(6, 'Боевик');

MERGE INTO ratings (id, "name") VALUES (1, 'G');
MERGE INTO ratings (id, "name") VALUES (2, 'PG');
MERGE INTO ratings (id, "name") VALUES (3, 'PG-13');
MERGE INTO ratings (id, "name") VALUES (4, 'R');
MERGE INTO ratings (id, "name") VALUES (5, 'NC-17');

--MERGE INTO films (id, "name", description, releasedate, duration, rating_id) values(1, 'Схватка', 'Описание фильма Схватка....', '2014-09-06', 85, 1);
--MERGE INTO films (id, "name", description, releasedate, duration, rating_id) values(2, 'Игла', 'Описание фильма Игла....', '2014-09-06', 45, 2);