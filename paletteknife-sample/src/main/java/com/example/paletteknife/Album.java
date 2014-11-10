package com.example.paletteknife;

public class Album {
    public final String artist;
    public final String name;
    public final String coverFileName;

    public Album(String artist, String name, String coverFileName) {
        this.artist = artist;
        this.name = name;
        this.coverFileName = coverFileName;
    }

    public String getCoverPath() {
        return "covers/" + coverFileName;
    }
}
