ALTER TABLE animusic.public.playlist
    ADD COLUMN cover_art_id INTEGER,
    ADD CONSTRAINT fk_playlist_cover_art_id
        FOREIGN KEY (cover_art_id)
            REFERENCES cover_art (id);