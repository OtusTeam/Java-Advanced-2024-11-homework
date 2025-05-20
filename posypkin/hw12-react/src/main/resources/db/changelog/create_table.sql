--liquibase formatted sql
--changeset Ilya Posypkin:1

CREATE TABLE IF NOT EXISTS users (
     id SERIAL PRIMARY KEY,
     login VARCHAR(50) NOT NULL UNIQUE,
     password VARCHAR(255) NOT NULL
);