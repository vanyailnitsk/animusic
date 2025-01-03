-- liquibase formatted sql
--changeset vanyailnitsk:create_album_subscription_table
create table subscription_for_album
(
    id       serial primary key,
    album_id integer not null,
    user_id  integer not null,
    added_at timestamp
);

--changeset vanyailnitsk:add_fk_album_subscription_to_user
alter table subscription_for_album
    add constraint fk_album_subscription_to_user foreign key (user_id) references users (id);

--changeset vanyailnitsk:add_fk_album_subscription_to_album
alter table subscription_for_album
    add constraint fk_album_subscription_to_album foreign key (album_id) references album (id);

--changeset vanyailnitsk:add_idx_album_subscription_for_user
create index idx_album_subscription_for_user on subscription_for_album (user_id)