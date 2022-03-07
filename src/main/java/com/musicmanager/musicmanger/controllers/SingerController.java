package com.musicmanager.musicmanger.controllers;

import java.util.List;
import java.util.UUID;

import com.musicmanager.musicmanger.model.ResponseObject;
import com.musicmanager.musicmanger.model.Singer;
import com.musicmanager.musicmanger.repositories.SingerRepository;

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
@RequestMapping(path="api/music-manager/singers")
public class SingerController {
    @Autowired 
    private SingerRepository repository;

    @GetMapping("/getAll")
    List<Singer> getAllGenre(){
        return repository.findAll();
    }
    private static final int PAGE_SIZE = 10;
    @GetMapping("")
    Page<Singer> genrePageable(@RequestParam int page) {
        Pageable paging = PageRequest.of(page-1, PAGE_SIZE, Sort.by("created").descending());
		return repository.findAll(paging);
	}
    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable UUID id){
        List<Singer> foundSingers = repository.findBySingerId(id);
        return (foundSingers.size() > 0) ? ResponseEntity.status(HttpStatus.OK).body(
        new ResponseObject("ok","ca si ton tai", foundSingers)
    ):ResponseEntity.status(HttpStatus.NOT_FOUND).body(
        new ResponseObject("fail","ca si khong ton tai", "")
    );
    }
    @PostMapping("")
    ResponseEntity<ResponseObject> insertMusic(@RequestBody Singer newSinger){
        List<Singer> foundSingers = repository.findBySingerName(newSinger.getSingerName().trim());
        return (foundSingers.size() == 0 ) ? 
        ResponseEntity.status(HttpStatus.OK).body(
            new ResponseObject("ok","them thanh cong",repository.save(newSinger))
            ): 
        ResponseEntity.status(HttpStatus.FOUND).body(
            new ResponseObject("fail","ca si da ton tai","")
        );

    }
}
