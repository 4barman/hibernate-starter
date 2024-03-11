CREATE TABLE users
(
    username   VARCHAR(128),
    firstname  VARCHAR(128),
    lastname   VARCHAR(128),
    birth_date DATE,
    age        INT
);

ALTER TABLE users
ADD COLUMN role VARCHAR(32);