-- liquibase formatted sql
--changeset vanyailnitsk:create_album_table
create table album
(
    id           serial primary key,
    name         varchar(255),
    anime_id     integer,
    cover_art_id integer
);

--changeset vanya:add_fk_album_to_anime
alter table album
    add constraint fk_album_to_anime foreign key (anime_id) references anime (id);

--changeset vanya:add_fk_album_to_cover_art
alter table album
    add constraint fk_album_to_cover_art foreign key (cover_art_id) references cover_art (id);
