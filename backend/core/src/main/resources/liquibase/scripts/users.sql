-- liquibase formatted sql
--changeset vanyainitsk:create_users_table
create table users
(
    id       serial primary key,
    email    varchar(255),
    password varchar(255),
    username varchar(255)
);