-- liquibase formatted sql
--changeset vanyailnitsk:create_playlist_table
create table playlist
(
    id           bigserial primary key,
    name         varchar(255),
    user_id      integer not null,
    cover_art_id integer
);

--changeset vanyailnitsk:add_fk_playlist_users
alter table playlist
    add constraint fk_playlist_users foreign key (user_id) references users (id);

--changeset vanyailnitsk:add_fk_playlist_cover_art
alter table playlist
    add constraint fk_playlist_cover_art foreign key (cover_art_id) references cover_art (id);
