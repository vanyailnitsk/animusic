-- Миграция для создания таблицы Image
CREATE TABLE images
(
    id     SERIAL PRIMARY KEY,
    source VARCHAR(255)
);


-- Миграция для добавления столбца image_id в таблицу cover_art
ALTER TABLE cover_art
    ADD COLUMN image_id INTEGER,
    ADD CONSTRAINT fk_cover_art_image
        FOREIGN KEY (image_id)
            REFERENCES images (id);
