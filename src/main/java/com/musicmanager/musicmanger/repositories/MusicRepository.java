package com.musicmanager.musicmanger.repositories;

import com.musicmanager.musicmanger.model.Music;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicRepository extends JpaRepository<Music,String> {
    
}
