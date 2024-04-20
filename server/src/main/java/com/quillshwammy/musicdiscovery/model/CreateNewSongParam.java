package com.quillshwammy.musicdiscovery.model;

import jakarta.validation.constraints.NotBlank;
import org.jetbrains.annotations.NotNull;

public class CreateNewSongParam {
    private String songName;
    @NotBlank
    private String artistName;

    public String getSongName() {
        return songName;
    }

    public void setSongName(@NotNull String songName) {
        this.songName = songName;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }
}
