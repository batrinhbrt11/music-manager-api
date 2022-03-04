package com.musicmanager.musicmanger.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tbleGenre")
public class Genre {   
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2") 
    @GeneratedValue(generator = "uuid2")
    private UUID genreId;
    private String genreName;
    private int songNum;
    public int getSongNum(){
        return songNum;
    }
    public Genre() {
    }
    public Genre(String genreName) {
      
        this.genreName = genreName;
    }
  
    public UUID getGenreId() {
        return genreId;
    }
    public void setGenreId(UUID genreId) {
        this.genreId = genreId;
    }
    public String getGenreName() {
        return genreName;
    }
    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }
}
