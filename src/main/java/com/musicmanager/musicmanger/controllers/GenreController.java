package com.musicmanager.musicmanger.controllers;


import java.util.List;

import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


import com.musicmanager.musicmanger.model.Genre;
import com.musicmanager.musicmanger.model.Music;
import com.musicmanager.musicmanger.model.ResponseObject;
import com.musicmanager.musicmanger.repositories.GenreRepository;
import com.musicmanager.musicmanger.repositories.MusicRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/music-manager/genres")
public class GenreController {
    @Autowired
    private GenreRepository repository;

    private static final Log log = LogFactory.getLog(GenreController.class);
    @Autowired
    private MusicRepository musicRepository;
    @GetMapping("/getAll")
    List<Genre> getAllGenre() {
        return repository.findAll();
    }

    private static final int PAGE_SIZE = 10;

    @GetMapping("")
    ResponseEntity<ResponseObject> genrePageable(@RequestParam int page) {
        Pageable paging = PageRequest.of(page-1, PAGE_SIZE, Sort.by("created").descending());
        JSONArray arr = new JSONArray();  
        for (Genre genre : repository.findAll(paging).getContent()) {
            List<Music> musics =  musicRepository.findAll().stream().filter(m-> m.getIdGenre().equals(genre.getGenreId()) ).collect(Collectors.toList());
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("genreId",genre.getGenreId());    
            jsonObject.put("genreName",genre.getGenreName());
            jsonObject.put("count",musics.size());
            arr.add(jsonObject);
            
        }
        return ResponseEntity.status(HttpStatus.OK).body(
            new ResponseObject("ok", "Sửa thể loại thành công", arr,repository.findAll(paging).getTotalElements()));
	}
    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable UUID id) {
        List<Genre> foundGenres = repository.findByGenreId(id);
        return (foundGenres.size() == 0) ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("fail", "the loai khong ton tai", ""))
                : ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "the loai ton tai", foundGenres));
    }

    @PostMapping("")
    ResponseEntity<ResponseObject> insertGenre(@RequestBody Genre newGenre) {
        List<Genre> foundGenres = repository.findByGenreName(newGenre.getGenreName().trim());
        return (foundGenres.size() == 0) ? ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "them thanh cong", repository.save(newGenre)))
                : ResponseEntity.status(HttpStatus.FOUND).body(
                        new ResponseObject("fail", "the loai da ton tai", ""));

    }

    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> updateGenre(@RequestBody Genre newGenre, @PathVariable UUID id) {
        Genre updatedGenre = repository.findById(id)
                .map(genre -> {
                    genre.setGenreName(newGenre.getGenreName());
                    return repository.save(genre);
                }).orElseGet(() -> {
                    return null;
                });
        if (updatedGenre != null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Sửa thể loại thành công", updatedGenre));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("fail", "Lỗi, sửa thất bại", ""));
        }
    }

  

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteGenre(@PathVariable UUID id) {
        boolean exists = repository.existsById(id);
        List<Music> musics =  musicRepository.findAll().stream().filter(m-> m.getIdGenre().equals(id) ).collect(Collectors.toList());;
        log.info(musics.size());
        if (exists) {
            if(musics.size() > 0){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("fail", "Thể loại có chứa bài hát", ""));
            }else{
                repository.deleteById(id);
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Xóa Thành công", ""));
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("fail", "Xóa thất bại", ""));
    }

}
