package com.musicmanager.musicmanger.controllers;

import java.util.List;
import java.util.Optional;

import com.musicmanager.musicmanger.model.Music;
import com.musicmanager.musicmanger.model.ResponseObject;
import com.musicmanager.musicmanger.repositories.MusicRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="api/musics")
public class MusicController {
    
    @Autowired
    private MusicRepository repository;
    
    @GetMapping("")
    List<Music> getAllMusic(){
        return repository.findAll();
    }

    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable String id){
        Optional<Music> foundMusic = repository.findById(id);
        return foundMusic.isPresent() ? ResponseEntity.status(HttpStatus.OK).body(
        new ResponseObject("ok","bai hat ton tai", foundMusic)
    ):ResponseEntity.status(HttpStatus.NOT_FOUND).body(
        new ResponseObject("fail","bai hat khong ton tai", "")
    );
    }
}
