-- liquibase formatted sql
--changeset vanyailnitsk:create_images_table
create table images
(
    id     serial primary key,
    source varchar(255)
);
