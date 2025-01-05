-- liquibase formatted sql
--changeset vanyailnitsk:create_anime_table
create table anime
(
    id            serial primary key,
    description   varchar(255),
    folder_name   varchar(255),
    release_year  integer,
    studio        varchar(255),
    title         varchar(255),
    banner_id     integer,
    card_image_id integer
);

--changeset vanyailnitsk:add_anime_unique_constraints
alter table anime
    add constraint uk_anime_folder_name_unique unique (folder_name);

alter table anime
    add constraint uk_anime_title_unique unique (title);

--changeset vanyailnitsk:add_fk_anime_banner
alter table anime
    add constraint fk_anime_banner foreign key (banner_id) references anime_banner (id);

--changeset vanyailnitsk:add_fk_anime_card_image
alter table anime
    add constraint fk_anime_card_image foreign key (card_image_id) references images (id);

--changeset vanyailnitsk:add_genre_column
alter table anime
    add column genre varchar(255)