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
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
@Entity
@Table(name = "tblSinger")
public class Singer {
    @Id
    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(name = "UUID", strategy = "uuid4")
    @Type(type="uuid-char")
    private UUID singerId;
    @Column(nullable = false,unique = true, length = 300)
    private String singerName;
    private Date singerBirthDay;
    private boolean singerSex;
    @Transient
    private int count;
    public int getCount() {
        return count;
    }
    public void setCount(int count) {}
        
    public Date getCreated() {
        return created;
    }
    public void setCreated(Date created) {
        this.created = created;
    }


    private String description;
    private String urlImage;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false)
    private Date created;
  
    public Singer() {
    }
    public Singer(String singerName) {
        this.singerName = singerName;
    }
    public Singer(String singerName, Date singerBirthDay, boolean singerSex, String description, String urlImage) {
        this.singerName = singerName;
        this.singerBirthDay = singerBirthDay;
        this.singerSex = singerSex;
        this.description = description;
        this.urlImage = urlImage;
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
    public Date getSingerBirthDay() {
        return singerBirthDay;
    }
    public void setSingerBirthDay(Date singerBirthDay) {
        this.singerBirthDay = singerBirthDay;
    }
    public boolean isSingerSex() {
        return singerSex;
    }
    public void setSingerSex(boolean singerSex) {
        this.singerSex = singerSex;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getUrlImage() {
        return urlImage;
    }
    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
   
    @PrePersist
    protected void onCreate() {
        created = new Date();
    }
}
