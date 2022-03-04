package com.musicmanager.musicmanger.model;

import java.util.Date;

public class Music {
    private String musicId;
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
    public String getMusicId() {
        return musicId;
    }
    public void setMusicId(String musicId) {
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
