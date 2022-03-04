package com.musicmanager.musicmanger.model;

import java.util.Date;

public class Singer {
    private String singerId;
    private String singerName;
    private Date singerBirthdy;
    private boolean singerSex;
    private int songNum;
    public Singer(String singerName, Date singerBirthdy, boolean singerSex, int songNum) {
        this.singerName = singerName;
        this.singerBirthdy = singerBirthdy;
        this.singerSex = singerSex;
        this.songNum = songNum;
    }
    public Singer(){
        
    }
    public String getSingerId() {
        return singerId;
    }
    public void setSingerId(String singerId) {
        this.singerId = singerId;
    }
    public String getSingerName() {
        return singerName;
    }
    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }
    public Date getSingerBirthdy() {
        return singerBirthdy;
    }
    public void setSingerBirthdy(Date singerBirthdy) {
        this.singerBirthdy = singerBirthdy;
    }
    public boolean isSingerSex() {
        return singerSex;
    }
    public void setSingerSex(boolean singerSex) {
        this.singerSex = singerSex;
    }
    public int getSongNum() {
        return songNum;
    }
    public void setSongNum(int songNum) {
        this.songNum = songNum;
    }
  
}
