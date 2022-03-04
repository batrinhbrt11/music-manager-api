package com.musicmanager.musicmanger.repositories;

import com.musicmanager.musicmanger.model.Genre;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre,String> {
    
}
