INSERT INTO anime (id, title, description, folder_name, release_year, studio)
VALUES (1, 'Anime-1', 'empty', 'Anime1', 2003, 'MAPPA');

INSERT INTO album(id, name, anime_id)
VALUES (1, 'Openings', 1);

INSERT INTO soundtrack (id, anime_title, original_title, audio_file, duration, anime_id, album_id)
VALUES (1, 'opening-1', 'track', 'path_to_audio.mp3', 255, 1, 1);

INSERT INTO soundtrack (id, anime_title, original_title, audio_file, duration, anime_id, album_id)
VALUES (2, 'opening-1', 'track', 'path_to_audio.mp3', 255, 1, 1);

INSERT INTO users (id, email, password, username)
VALUES (1, 'email', 'password', 'username');

INSERT INTO playlist (id, name, user_id)
VALUES (1, 'Favourite', 1);

INSERT INTO playlist_soundtrack (added_at, playlist_id, soundtrack_id)
VALUES (now(), 1, 1);

INSERT INTO playlist_soundtrack (added_at, playlist_id, soundtrack_id)
VALUES (now(), 1, 2);

select setval('soundtrack_id_seq', (select max(id) from soundtrack));
