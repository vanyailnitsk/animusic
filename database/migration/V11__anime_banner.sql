ALTER TABLE anime
    ADD COLUMN banner_id INTEGER,
    ADD CONSTRAINT fk_anime_banner_image
        FOREIGN KEY (banner_id)
            REFERENCES anime_banner (id);

-- Создание объектов Image из значений bannerImagePath в каждой записи таблицы anime
INSERT INTO images (source)
SELECT banner_image_path FROM anime;

-- Создание объектов AnimeBanner на основе созданных объектов Image
INSERT INTO anime_banner (color, image_id)
SELECT '#000000', id FROM images;

-- Присвоение id созданных объектов AnimeBanner в таблице anime
UPDATE anime AS a
SET banner_id = ab.id
FROM anime_banner AS ab
WHERE a.banner_image_path = (SELECT source FROM images WHERE id = ab.image_id);
