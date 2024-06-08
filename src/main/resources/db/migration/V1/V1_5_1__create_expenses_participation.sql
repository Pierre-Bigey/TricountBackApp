CREATE TABLE expense_participation (
    id bigserial,
    expense_id bigint NOT NULL,
    user_id bigint NOT NULL,
    weight integer NOT NULL DEFAULT 1,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    PRIMARY KEY (expense_id, user_id),
    FOREIGN KEY (expense_id) REFERENCES expense(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES user_account(id) ON DELETE CASCADE
);