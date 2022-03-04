package com.musicmanager.musicmanger.repositories;

import com.musicmanager.musicmanger.model.Singer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SingerRepository extends JpaRepository<Singer,String>{
    
}
