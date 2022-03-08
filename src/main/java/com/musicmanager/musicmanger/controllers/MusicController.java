package com.musicmanager.musicmanger.controllers;

import java.util.ArrayList;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.musicmanager.musicmanger.Pagination;
import com.musicmanager.musicmanger.model.Music;
import com.musicmanager.musicmanger.model.ResponseObject;
import com.musicmanager.musicmanger.repositories.MusicRepository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping(path = "api/music-manager/musics")
public class MusicController {
   private static final Log log = LogFactory.getLog(MusicController.class);
    private static final int PAGE_SIZE = 10;
    @Autowired
    private MusicRepository repository;

    @GetMapping("/getAll")
    List<Music> getAllMusic() {
        ArrayList<Music> musics = (ArrayList<Music>) repository.findAll();
        Comparator<Music> compareCreateDate = (Music m1, Music m2) -> m1.getCreated().compareTo(m2.getCreated());
        Collections.sort(musics, compareCreateDate.reversed());
        
        return musics;
    }

    @GetMapping("")
    Page<Music> musicPageable(@RequestParam int page) {
        Pageable paging = PageRequest.of(page - 1, PAGE_SIZE, Sort.by("created").descending());
        return  repository.findAll(paging);
    }

    @GetMapping("/genre/{idGenre}")
    ResponseEntity<ResponseObject> getMusicByGenre(@PathVariable UUID idGenre,@RequestParam int page){
        
        ArrayList<Music> musics = (ArrayList<Music>) repository.findAll().stream().filter(m-> m.getIdGenre().equals(idGenre) ).collect(Collectors.toList());
        Comparator<Music> compareCreateDate = (Music m1, Music m2) -> m1.getCreated().compareTo(m2.getCreated());
        Collections.sort(musics, compareCreateDate.reversed());
        Pagination paging = new Pagination(page,musics);
        log.info(paging.paginationsList());
        return ResponseEntity.status(HttpStatus.OK).body(
            new ResponseObject("ok", "Danh sách",musics.size(), paging.paginationsList()));
    }
    
    @GetMapping("/singer/{idSinger}")
    ResponseEntity<ResponseObject> getMusicBySinger(@PathVariable UUID idSinger,@RequestParam int page){
        
        ArrayList<Music> musics = (ArrayList<Music>) repository.findAll().stream().filter(m-> m.getIdSinger().equals(idSinger) ).collect(Collectors.toList());
        Comparator<Music> compareCreateDate = (Music m1, Music m2) -> m1.getCreated().compareTo(m2.getCreated());
        Collections.sort(musics, compareCreateDate.reversed());
        Pagination paging = new Pagination(page,musics);
        return ResponseEntity.status(HttpStatus.OK).body(
            new ResponseObject("ok", "Danh sách",musics.size(), paging.paginationsList()));
    }
    
    @GetMapping("/playlist")
    ResponseEntity<ResponseObject> getPlaylist(@RequestParam int page){
        
        ArrayList<Music> musics = (ArrayList<Music>) repository.findAll().stream().filter(m-> m.getIsPlayList() ).collect(Collectors.toList());
        Comparator<Music> compareCreateDate = (Music m1, Music m2) -> m1.getCreated().compareTo(m2.getCreated());
        Collections.sort(musics, compareCreateDate.reversed());
        Pagination paging = new Pagination(page,musics);
        return ResponseEntity.status(HttpStatus.OK).body(
            new ResponseObject("ok", "Danh sách",musics.size(), paging.paginationsList()));
    }

    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable UUID id) {
        List<Music> foundMusic = repository.findByMusicId(id);
        return (foundMusic.size() > 0) ? ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "", foundMusic))
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("fail", "Bài hát không tồn tại", ""));
    }

    @PostMapping("")
    ResponseEntity<ResponseObject> insertMusic(@RequestBody Music newMusic) {
        List<Music> foundMusic = repository.findByMusicName(newMusic.getMusicName().trim());
        try {
            return (foundMusic.size() == 0) ? ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Thêm bài hát thành công", repository.save(newMusic)))
                    : ResponseEntity.status(HttpStatus.FOUND).body(
                            new ResponseObject("fail", "Tên bài hát đã tồn tại, vui lòng nhập tên khác", ""));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("fail", "Lỗi, thêm không thành công", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> updateMusic(@RequestBody Music newMusic, @PathVariable UUID id) {
        Music updatedMusic = repository.findById(id)
                .map(music -> {
                    music.setMusicName(newMusic.getMusicName());
                    music.setIdGenre(newMusic.getIdGenre());
                    music.setIdSinger(newMusic.getIdSinger());
                    music.setRealeaseTime(newMusic.getRealeaseTime());
                    music.setUrlFile(newMusic.getUrlFile());
                    return repository.save(music);
                }).orElseGet(() -> {
                    return null;
                });
        if(updatedMusic != null){
            return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Sửa bài hát thành công", updatedMusic));
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject("fail", "Lỗi, sửa thất bại", ""));
        }
    }

    @PutMapping("/playlist/{id}")
    ResponseEntity<ResponseObject> addPlaylist(@PathVariable UUID id){
        Music updatedMusic = repository.findById(id).map(music->{
            music.setPlayList(!music.getIsPlayList());
            return repository.save(music);
        }).orElseGet(()->{
            return null;
        });
        if(updatedMusic != null){
            return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Thêm vào playlist thành công", updatedMusic));
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject("fail", "Lỗi, thêm vào playlist thất bại", ""));
        }
    }
    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteMusic(@PathVariable UUID id) {
        try {
            Optional<Music> foundMusic = repository.findById(id);
            if (foundMusic.get().getIsPlayList() == true) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new ResponseObject("fail", "Bài hát có trong playlist không thể xóa", ""));
            } else {
                repository.deleteById(id);
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Xóa thành công", ""));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("fail", "Không tìm thấy bài hát này", e.getMessage()));
        }
    }

}
