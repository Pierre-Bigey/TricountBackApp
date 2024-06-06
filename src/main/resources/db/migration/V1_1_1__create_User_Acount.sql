CREATE TABLE user_account (
    id bigserial PRIMARY KEY,
    username varchar(255) NOT NULL,
    firstname varchar(255) NOT NULL,
    lastname varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    created_at date NOT NULL,
    updated_at date NOT NULL
    );