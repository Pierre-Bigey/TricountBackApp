CREATE TABLE expense_group (
    id bigserial PRIMARY KEY,
    description varchar(255) NOT NULL,
    groupname varchar(255) NOT NULL,
    created_at date NOT NULL,
    updated_at date NOT NULL
);