package com.musicmanager.musicmanger.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
      
        Date date = new Date(2000,11,20);
        Music music = new Music("","","",false,date);
        List<Music> list = new ArrayList<Music>(Arrays.asList(music));
        return list;
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
