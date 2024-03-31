ALTER TABLE playlist RENAME TO album;

ALTER TABLE soundtrack ADD COLUMN album_id  integer
    constraint fk_soundtrack_album
        references album;

UPDATE soundtrack s SET album_id = ps.playlist_id
    FROM playlist_soundtrack ps
    WHERE s.id = ps.soundtrack_id;
