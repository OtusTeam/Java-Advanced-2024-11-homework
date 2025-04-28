DROP TABLE IF EXISTS app_users;

CREATE TABLE app_users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    login VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    password_hash VARCHAR(255)
);