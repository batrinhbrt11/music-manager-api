package com.musicmanager.musicmanger.model;

public class Genre {
    private String genreId;
    private String genreName;
    private int songNum;
    public int getSongNum(){
        return songNum;
    }
    public Genre() {
    }
    public Genre(String genreId, String genreName) {
        this.genreId = genreId;
        this.genreName = genreName;
    }
  
    public String getGenreId() {
        return genreId;
    }
    public void setGenreId(String genreId) {
        this.genreId = genreId;
    }
    public String getGenreName() {
        return genreName;
    }
    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }
}
