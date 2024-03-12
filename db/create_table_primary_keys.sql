CREATE TABLE users
(

    firstname  VARCHAR(128),
    lastname   VARCHAR(128),
    birth_date DATE,
    role       VARCHAR(32),
    age        INT,
    info       JSONB,
    username   VARCHAR(128) UNIQUE ,
    PRIMARY KEY (firstname, lastname, birth_date)
);

DROP TABLE users;