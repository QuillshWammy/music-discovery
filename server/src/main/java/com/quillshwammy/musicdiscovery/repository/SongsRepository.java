package com.quillshwammy.musicdiscovery.repository;

import com.quillshwammy.musicdiscovery.model.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SongsRepository {
    private final JdbcTemplate jdbcTemplate;
    private final DataClassRowMapper<Song> mapper = new DataClassRowMapper<>(Song.class);

    @Autowired
    public SongsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Song> listAll() {
        return jdbcTemplate.query("SELECT * FROM songs", (rs, rowNum) -> {
            Song songs = new Song();
            songs.setId(rs.getInt("id"));
            songs.setSongName(rs.getString("song_name"));
            songs.setArtistName(rs.getString("artist_name"));
            songs.setRecordDate(rs.getTimestamp("record_date").toLocalDateTime());
            return songs;
        });
    }

    public Song register(Song song) {
        return this.jdbcTemplate.query(
                "INSERT INTO songs (song_name, artist_name) VALUES (?, ?) RETURNING *",
                mapper,
                song.getSongName(),
                song.getArtistName()
        ).get(0);
    }
}
