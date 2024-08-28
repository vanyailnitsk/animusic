INSERT INTO anime (id, title, description, folder_name, release_year, studio)
VALUES (1, 'Anime-1', 'empty', 'anime1', 2003, 'MAPPA');

INSERT INTO album(id, name, anime_id)
VALUES (1, 'Openings', 1);

INSERT INTO soundtrack (id, anime_title, original_title, audio_file, duration, anime_id, album_id)
VALUES (1, 'opening-1', 'track', 'path_to_audio.mp3', 255, 1, 1);