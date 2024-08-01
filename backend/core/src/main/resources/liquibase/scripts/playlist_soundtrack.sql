-- liquibase formatted sql
--changeset vanyailnitsk:create_playlist_soundtrack_table
create table playlist_soundtrack
(
    id          bigserial primary key,
    added_at      timestamp(6),
    playlist_id integer not null,
    soundtrack_id integer not null
);

--changeset vanyailnitsk:add_fk_playlist_soundtrack_playlist
alter table playlist_soundtrack
    add constraint fk_playlist_soundtrack_playlist foreign key (playlist_id) references playlist (id);

--changeset vanyailnitsk:add_fk_playlist_soundtrack_to_soundtrack
alter table playlist_soundtrack
    add constraint fk_playlist_soundtrack_to_soundtrack foreign key (soundtrack_id) references soundtrack (id);
