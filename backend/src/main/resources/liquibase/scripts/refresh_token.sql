-- liquibase formatted sql
--changeset vanyailnitsk:create_refresh_token_table
create table refresh_token
(
    id          bigserial primary key,
    expire_date timestamp(6) with time zone not null,
    token       varchar(255)                not null,
    user_id     integer                     not null
);

--changeset vanyailnitsk:add_refresh_token_unique_constraints
alter table refresh_token
    add constraint uk_refresh_token_unique unique (token);

--changeset vanyailnitsk:add_fk_refresh_token_users
alter table refresh_token
    add constraint fk_refresh_token_users foreign key (user_id) references users (id);
