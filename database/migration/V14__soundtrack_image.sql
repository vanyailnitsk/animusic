ALTER TABLE soundtrack
    ADD COLUMN image_id INTEGER,
    ADD CONSTRAINT fk_soundtrack_image
        FOREIGN KEY (image_id)
            REFERENCES images (id);

-- Создание объектов Image на основе значений image_id в каждой записи таблицы soundtrack
INSERT INTO images (source)
SELECT image_file FROM soundtrack;

-- Присвоение id созданных объектов Image в таблице soundtrack
UPDATE soundtrack AS s
SET image_id = i.id
FROM images AS i
WHERE s.image_file = i.source;

