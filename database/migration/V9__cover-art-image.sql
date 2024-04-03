ALTER TABLE cover_art ADD COLUMN image_url varchar(255);

ALTER TABLE album DROP COLUMN image_url;