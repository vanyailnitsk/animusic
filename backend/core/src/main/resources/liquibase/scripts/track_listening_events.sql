-- liquibase formatted sql
--changeset vanyailnitsk:create_track_listening_events_table
CREATE TABLE track_listening_events
(
    id          SERIAL PRIMARY KEY,
    track_id    INTEGER   NOT NULL,
    user_id     INTEGER,
    listened_at TIMESTAMP NOT NULL
);

--changeset vanyailnitsk:add_fk_track_listening_events_soundtrack
alter table track_listening_events
    add constraint fk_track_listening_events_soundtrack foreign key (track_id) references soundtrack (id);

--changeset vanyailnitsk:add_fk_track_listening_events_user
alter table track_listening_events
    add constraint fk_track_listening_events_user foreign key (user_id) references users (id);
