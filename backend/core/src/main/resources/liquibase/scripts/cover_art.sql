-- liquibase formatted sql
--changeset vanyailnitsk:create_cover_art_table
create table cover_art
(
    id          serial primary key,
    color_light varchar(7),
    color_dark  varchar(7),
    image_id    integer
);

--changeset vanyailnitsk:add_fk_cover_art_image
alter table cover_art
    add constraint fk_cover_art_image foreign key (image_id) references images (id);
