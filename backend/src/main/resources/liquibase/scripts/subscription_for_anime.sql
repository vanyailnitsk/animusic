-- liquibase formatted sql
--changeset vanyailnitsk:create_anime_subscription_table
create table subscription_for_anime
(
    id       serial primary key,
    anime_id integer not null,
    user_id  integer not null,
    added_at timestamp
);

--changeset vanyailnitsk:add_fk_anime_subscription_to_user
alter table subscription_for_anime
    add constraint fk_anime_subscription_to_user foreign key (user_id) references users (id);

--changeset vanyailnitsk:add_fk_anime_subscription_to_anime
alter table subscription_for_anime
    add constraint fk_anime_subscription_to_anime foreign key (anime_id) references anime (id);

--changeset vanyailnitsk:add_idx_anime_subscription_for_user
create index idx_anime_subscription_for_user on subscription_for_anime (user_id)