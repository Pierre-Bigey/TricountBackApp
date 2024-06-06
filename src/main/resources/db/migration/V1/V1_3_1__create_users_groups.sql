CREATE TABLE users_groups (
    user_id bigint NOT NULL,
    group_id bigint NOT NULL,
    PRIMARY KEY (user_id, group_id),
    FOREIGN KEY (user_id) REFERENCES user_account(id) ON DELETE CASCADE,
    FOREIGN KEY (group_id) REFERENCES expense_group(id) ON DELETE CASCADE
);