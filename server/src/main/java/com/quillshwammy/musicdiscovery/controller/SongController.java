package com.quillshwammy.musicdiscovery.controller;

import com.quillshwammy.musicdiscovery.model.CreateNewSongParam;
import com.quillshwammy.musicdiscovery.model.Song;
import com.quillshwammy.musicdiscovery.repository.SongsRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/songs")
public class SongController {
    private final SongsRepository songsRepository;

    @Autowired
    public SongController(SongsRepository songsRepository) {
        this.songsRepository = songsRepository;
    }

    @GetMapping
    public List<Song> listAll() {
        return songsRepository.listAll();
    }

    @PostMapping
    public Song create(@RequestBody @Valid CreateNewSongParam param) {
        Song song = new Song();
        song.setSongName(param.getSongName());
        song.setArtistName(param.getArtistName());

        return songsRepository.register(song);
    }
}
