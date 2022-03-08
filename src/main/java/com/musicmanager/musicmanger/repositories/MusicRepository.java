package com.musicmanager.musicmanger.repositories;


import java.util.List;

import java.util.UUID;


import com.musicmanager.musicmanger.model.Music;

import org.springframework.data.jpa.repository.JpaRepository;


public interface MusicRepository extends JpaRepository<Music,UUID> {
    List<Music> findByMusicName(String musicName);
    List<Music> findByMusicId(UUID musicId);
    
   
}
