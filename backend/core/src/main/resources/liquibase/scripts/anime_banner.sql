-- liquibase formatted sql
--changeset vanyailnitsk:create_anime_banner_table
create table anime_banner
(
    id       serial primary key,
    color    varchar(7),
    image_id integer
);

--changeset vanyailnitsk:add_fk_anime_banner_image
alter table anime_banner
    add constraint fk_anime_banner_image foreign key (image_id) references images (id);
