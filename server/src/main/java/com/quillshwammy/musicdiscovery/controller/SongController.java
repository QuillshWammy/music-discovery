package com.quillshwammy.musicdiscovery.controller;

import com.quillshwammy.musicdiscovery.model.Songs;
import com.quillshwammy.musicdiscovery.repository.SongsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public List<Songs> listAll() {
        return songsRepository.listAll();
    }
    
}
