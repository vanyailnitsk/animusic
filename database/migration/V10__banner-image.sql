-- Миграция для создания таблицы BannerImage
CREATE TABLE anime_banner
(
    id       SERIAL PRIMARY KEY,
    color    VARCHAR(7),
    image_id INTEGER,
    CONSTRAINT fk_anime_banner_image
        FOREIGN KEY (image_id)
            REFERENCES images (id)
);
