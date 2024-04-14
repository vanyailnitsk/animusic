CREATE TABLE user_roles
(
    user_id BIGINT       NOT NULL,
    roles    VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id)
);