ALTER TABLE soundtrack RENAME COLUMN path_to_file to audio_file;

ALTER TABLE soundtrack ADD COLUMN image_file character varying(255);
