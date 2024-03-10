create table if not exists token (id bigserial not null, value varchar(255), primary key (id));
create table if not exists users (id serial not null, email varchar(255), password varchar(255), username varchar(255), favourite_playlist_id bigint, primary key (id));


