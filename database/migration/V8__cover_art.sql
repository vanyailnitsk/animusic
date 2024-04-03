CREATE TABLE cover_art
(
    id          SERIAL PRIMARY KEY,
    color_light VARCHAR(7),
    color_dark  VARCHAR(7)
);

ALTER TABLE album
    ADD COLUMN cover_art_id INTEGER,
    ADD CONSTRAINT fk_cover_art_id
        FOREIGN KEY (cover_art_id)
            REFERENCES cover_art (id);