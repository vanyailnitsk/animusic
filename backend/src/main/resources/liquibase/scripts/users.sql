-- liquibase formatted sql
--changeset vanyainitsk:create_users_table
create table users
(
    id       serial primary key,
    email    varchar(255),
    password varchar(255),
    username varchar(255)
);

--changeset vanyailnitsk:add_username_unique_constraints
alter table users
    add constraint uk_username_unique_constraints unique (username);

--changeset vanyailnitsk:add_oauth_fields
alter table users add column github_id varchar(100);
alter table users add column google_id varchar(100);