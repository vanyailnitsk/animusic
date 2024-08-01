-- liquibase formatted sql
--changeset vanyailnitsk:create_user_roles_table
create table user_roles
(
    user_id integer      not null,
    roles   varchar(255) not null
);

--changeset vanyailnitsk:add_fk_user_roles_users
alter table user_roles
    add constraint fk_user_roles_users foreign key (user_id) references users (id);
