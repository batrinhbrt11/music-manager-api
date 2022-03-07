package com.musicmanager.musicmanger.repositories;

import java.util.Date;
import java.util.List;

import java.util.UUID;

import javax.transaction.Transactional;

import com.musicmanager.musicmanger.model.Music;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MusicRepository extends JpaRepository<Music,UUID> {
    List<Music> findByMusicName(String musicName);
    List<Music> findByMusicId(UUID musicId);
    @Transactional
    @Modifying
    @Query("update Music m set m.musicName =?1, m.idGenre=?2, m.idSinger=?3 , m.isPlayList=?4,m.realeaseTime=?5, m.urlFile=?6 where m.musicId=?7")
    int updatedMusic(String musicName , UUID idGenre , UUID idSinger, boolean isPlayList, Date realeaseTime , String urlFile, UUID musicId );

}
