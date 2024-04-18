package com.quillshwammy.musicdiscovery.repository;

import com.quillshwammy.musicdiscovery.model.Songs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SongsRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SongsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Songs> listAll() {
        return jdbcTemplate.query("SELECT * FROM songs", (rs, rowNum) -> {
            Songs songs = new Songs();
            songs.setId(rs.getInt("id"));
            songs.setSongName(rs.getString("song_name"));
            songs.setArtistName(rs.getString("artist_name"));
            songs.setRecordDate(rs.getTimestamp("record_date").toLocalDateTime());
            return songs;
        });
    }


}
