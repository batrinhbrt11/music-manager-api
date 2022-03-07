package com.musicmanager.musicmanger.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name="tblMusic")
public class Music {
    @Id
    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(name = "UUID", strategy = "uuid4")
    @Type(type="uuid-char")
    private UUID musicId;
    @Column(nullable = false,unique = true, length = 300)
    private String musicName;
    @GenericGenerator(name = "UUID", strategy = "uuid4")
    @Type(type="uuid-char")
    private UUID idGenre;
    @GenericGenerator(name = "UUID", strategy = "uuid4")
    @Type(type="uuid-char")
    private UUID idSinger;
    private boolean isPlayList;
    private Date realeaseTime;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false)
    private Date created;
    public Music() {
    }
    public Date getCreated() {
        return created;
    }
    public void setCreated(Date created) {
        this.created = created;
    }
    public Music(String musicName, UUID idGenre, UUID idSinger, boolean isPlayList, Date realeaseTime, String urlFile) {
        this.musicName = musicName;
        this.idGenre = idGenre;
        this.idSinger = idSinger;
        this.isPlayList = isPlayList;
        this.realeaseTime = realeaseTime;
        this.urlFile = urlFile;
    }
    private String urlFile;
    @Override
    public String toString() {
        return "Music [idGenre=" + idGenre + ", idSinger=" + idSinger + ", isPlayList=" + isPlayList + ", musicId="
                + musicId + ", musicName=" + musicName + ", realeaseTime=" + realeaseTime + ", urlFile=" + urlFile
                + "]";
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
    public UUID getIdGenre() {
        return idGenre;
    }
    public void setIdGenre(UUID idGenre) {
        this.idGenre = idGenre;
    }
    public UUID getIdSinger() {
        return idSinger;
    }
    public void setIdSinger(UUID idSinger) {
        this.idSinger = idSinger;
    }
    public boolean getIsPlayList() {
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
    public String getUrlFile() {
        return urlFile;
    }
    public void setUrlFile(String urlFile) {
        this.urlFile = urlFile;
    }
    @PrePersist
    protected void onCreate() {
        created = new Date();
    }
    
}
