package com.musicmanager.musicmanger.repositories;

import java.util.List;
import java.util.UUID;

import com.musicmanager.musicmanger.model.Singer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SingerRepository extends JpaRepository<Singer,UUID>{
    List<Singer> findBySingerId(UUID singerId);
    List<Singer> findBySingerName(String singerName);
}
