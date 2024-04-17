CREATE TABLE IF NOT EXISTS songs (
    id serial PRIMARY KEY,
    song_name character varying(255),
    artist_name character varying(255),
    record_date date DEFAULT CURRENT_DATE
);
