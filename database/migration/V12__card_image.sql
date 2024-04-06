ALTER TABLE anime
    ADD COLUMN card_image_id INTEGER,
    ADD CONSTRAINT fk_anime_card_image
        FOREIGN KEY (card_image_id)
            REFERENCES images (id);

-- Создание объектов Image на основе значений card_image_path в каждой записи таблицы anime
INSERT INTO images (source)
SELECT card_image_path FROM anime;

-- Присвоение id созданных объектов Image в таблице anime
UPDATE anime AS a
SET card_image_id = i.id
FROM images AS i
WHERE a.card_image_path = i.source;

