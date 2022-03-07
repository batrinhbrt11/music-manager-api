package com.musicmanager.musicmanger.repositories;

import java.util.List;
import java.util.UUID;

import com.musicmanager.musicmanger.model.Genre;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre,UUID> {
    List<Genre> findByGenreId(UUID genreId);
    List<Genre> findByGenreName(String genreName);
}
