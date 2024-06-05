CREATE TABLE expense (
    id bigserial PRIMARY KEY,
    title varchar(255) NOT NULL,
    description varchar(255),
    amount integer NOT NULL,
    created_at date NOT NULL,
    updated_at date NOT NULL,
    author_id bigserial NOT NULL,
    group_id bigserial NOT NULL,
    CONSTRAINT author_fk FOREIGN KEY (author_id) REFERENCES user_account (id)  ON DELETE CASCADE,
    CONSTRAINT group_fk FOREIGN KEY (group_id) REFERENCES expense_group (id)  ON DELETE CASCADE
);