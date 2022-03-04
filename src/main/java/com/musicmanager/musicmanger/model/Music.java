package com.musicmanager.musicmanger.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="tblMusic")
public class Music {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2") 
    @GeneratedValue(generator = "uuid2")
    private UUID musicId;
    private String musicName;
    private String idGenre;
    private String idSinger;
    private boolean isPlayList;
    private Date realeaseTime;
    public Music( String musicName, String idGenre, String idSinger, boolean isPlayList,
            Date realeaseTime) {
       
        this.musicName = musicName;
        this.idGenre = idGenre;
        this.idSinger = idSinger;
        this.isPlayList = isPlayList;
        this.realeaseTime = realeaseTime;
    }
    public Music(){

    }
    
    public UUID getMusicId() {
        return musicId;
    }
    public void setMusicId(UUID musicId) {
        this.musicId = musicId;
    }
    public String getMusicName() {
        return musicName;
    }
    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }
    public String getIdGenre() {
        return idGenre;
    }
    public void setIdGenre(String idGenre) {
        this.idGenre = idGenre;
    }
    public String getIdSinger() {
        return idSinger;
    }
    public void setIdSinger(String idSinger) {
        this.idSinger = idSinger;
    }
    public boolean isPlayList() {
        return isPlayList;
    }
    public void setPlayList(boolean isPlayList) {
        this.isPlayList = isPlayList;
    }
    public Date getRealeaseTime() {
        return realeaseTime;
    }
    public void setRealeaseTime(Date realeaseTime) {
        this.realeaseTime = realeaseTime;
    }
    
    
    
}
