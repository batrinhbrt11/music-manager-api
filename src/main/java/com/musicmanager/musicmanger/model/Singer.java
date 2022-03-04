package com.musicmanager.musicmanger.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name = "tblSinger")
public class Singer {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2") 
    @GeneratedValue(generator = "uuid2")
    private UUID singerId;
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
    public UUID getSingerId() {
        return singerId;
    }
    public void setSingerId(UUID singerId) {
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
