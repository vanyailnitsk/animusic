create table if not exists user_playlist (id bigserial not null, name varchar(255), user_id integer, primary key (id));
create table if not exists user_playlist_soundtrack (id bigserial not null, added_at timestamp(6), playlist_id bigint, soundtrack_id integer, primary key (id));

alter table if exists user_playlist add constraint FKlrogpjyk21nxavlbum7tj11el foreign key (user_id) references users;
alter table if exists user_playlist_soundtrack add constraint FKkxcad2tr797blkal12aog6kin foreign key (playlist_id) references user_playlist;
alter table if exists user_playlist_soundtrack add constraint FKrtuk7cb8rsvdc8oyt2w2366o2 foreign key (soundtrack_id) references soundtrack;
alter table if exists users add constraint FK79uhjtb003vdqrw5mu7bcooad foreign key (favourite_playlist_id) references user_playlist;
