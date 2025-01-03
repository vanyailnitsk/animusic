INSERT INTO images (id, source)
VALUES (1, 's3:image-1');

INSERT INTO images (id, source)
VALUES (2, 's3:image-2');

INSERT INTO images (id, source)
VALUES (3, 's3:image-3');

INSERT INTO anime_banner (id, color, image_id)
VALUES (1, '#ff0000', 1);

INSERT INTO anime_banner (id, color, image_id)
VALUES (2, '#ff0000', 2);

INSERT INTO anime_banner (id, color, image_id)
VALUES (3, '#0000ff', 3);

INSERT INTO anime (id, title, description, folder_name, release_year, studio, banner_id, card_image_id)
VALUES (1, 'Anime-1', 'empty', 'anime1', 2003, 'MAPPA', 1, 1);

INSERT INTO anime (id, title, description, folder_name, release_year, studio, banner_id, card_image_id)
VALUES (2, 'Anime-2', 'empty', 'anime2', 2003, 'MAPPA', 2, 2);

INSERT INTO anime (id, title, description, folder_name, release_year, studio, banner_id, card_image_id)
VALUES (3, 'Anime-3', 'empty', 'anime3', 2003, 'MAPPA', 3, 3);

INSERT INTO anime (id, title, description, folder_name, release_year, studio)
VALUES (4, 'Anime-4', 'empty', 'anime4', 2003, 'MAPPA');

-- Need to set new id after using manual inserting ids
select setval('anime_id_seq', (select max(id) from anime));