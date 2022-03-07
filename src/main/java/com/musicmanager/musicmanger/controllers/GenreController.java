package com.musicmanager.musicmanger.controllers;

import java.util.List;
import java.util.UUID;

import com.musicmanager.musicmanger.model.Genre;
import com.musicmanager.musicmanger.model.ResponseObject;
import com.musicmanager.musicmanger.repositories.GenreRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="api/music-manager/genres")
public class GenreController {
    @Autowired 
    private GenreRepository repository;
  
    @GetMapping("/getAll")
    List<Genre> getAllGenre(){
        return repository.findAll();
    }
    private static final int PAGE_SIZE = 10;
    @GetMapping("")
    Page<Genre> genrePageable(@RequestParam int page) {
        Pageable paging = PageRequest.of(page-1, PAGE_SIZE, Sort.by("created").descending());
		return repository.findAll(paging);
	}
    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable UUID id){
        List<Genre> foundGenres = repository.findByGenreId(id);
        return (foundGenres.size() == 0) ?ResponseEntity.status(HttpStatus.NOT_FOUND).body(
        new ResponseObject("fail","the loai khong ton tai", "")
    ): ResponseEntity.status(HttpStatus.OK).body(
        new ResponseObject("ok","the loai ton tai", foundGenres)
    );
    }
    @PostMapping("")
    ResponseEntity<ResponseObject> insertMusic(@RequestBody Genre newGenre){
        List<Genre> foundGenres = repository.findByGenreName(newGenre.getGenreName().trim());
        return (foundGenres.size() == 0 ) ? 
        ResponseEntity.status(HttpStatus.OK).body(
            new ResponseObject("ok","them thanh cong",repository.save(newGenre))
            ): 
        ResponseEntity.status(HttpStatus.FOUND).body(
            new ResponseObject("fail","the loai da ton tai","")
        );

    }

}
