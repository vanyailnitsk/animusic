-- liquibase formatted sql
--changeset vanyailnitsk:create_soundtrack_table
create table soundtrack
(
    id serial primary key,
    anime_title    varchar(255),
    original_title varchar(255),
    audio_file     varchar(255),
    anime_id       integer not null,
    duration       integer,
    album_id       integer not null,
    image_id       integer
);

--changeset vanyailnitsk:add_fk_soundtrack_anime
alter table soundtrack
    add constraint fk_soundtrack_anime foreign key (anime_id) references anime (id);

--changeset vanyailnitsk:add_fk_soundtrack_album
alter table soundtrack
    add constraint fk_soundtrack_album foreign key (album_id) references album (id);

--changeset vanyailnitsk:add_fk_soundtrack_image
alter table soundtrack
    add constraint fk_soundtrack_image foreign key (image_id) references images (id);

--changeset vanyailnitsk:add_creation_date
alter table soundtrack
    add column creation_date timestamp;