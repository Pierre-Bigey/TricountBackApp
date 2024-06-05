CREATE TABLE expense_participation (
    id bigserial PRIMARY KEY,
    expense_id bigint NOT NULL,
    user_id bigint NOT NULL,
    weight integer NOT NULL DEFAULT 1,
    created_at date NOT NULL,
    updated_at date NOT NULL,
    FOREIGN KEY (expense_id) REFERENCES expense(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES user_account(id) ON DELETE CASCADE
);