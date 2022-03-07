package com.musicmanager.musicmanger.controllers;

import java.util.ArrayList;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
@RequestMapping(path="api/music-manager/musics")
public class MusicController {
    private static final Log log = LogFactory.getLog(MusicController.class);
    private static final int PAGE_SIZE = 10;
    @Autowired
    private MusicRepository repository;
    @GetMapping("/getAll")
    List<Music> getAllMusic(){
        ArrayList<Music> musics= (ArrayList<Music>) repository.findAll();
        Comparator<Music> compareCreateDate = (Music m1, Music m2)->m1.getCreated().compareTo(m2.getCreated());
        Collections.sort(musics,compareCreateDate.reversed());
        return musics;
    }
    @GetMapping("")
    Page<Music> musicPageable(@RequestParam int page) {
        Pageable paging = PageRequest.of(page-1, PAGE_SIZE, Sort.by("created").descending());
		return repository.findAll(paging);
	}
    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable UUID id){
        List<Music> foundMusic = repository.findByMusicId(id);
        return (foundMusic.size() > 0) ? ResponseEntity.status(HttpStatus.OK).body(
        new ResponseObject("ok","", foundMusic)
    ):ResponseEntity.status(HttpStatus.NOT_FOUND).body(
        new ResponseObject("fail","Bài hát không tồn tại", "")
    );
    }
    @PostMapping("")
    ResponseEntity<ResponseObject> insertMusic(@RequestBody Music newMusic){
        List<Music> foundMusic = repository.findByMusicName(newMusic.getMusicName().trim());
        try {
            return (foundMusic.size() == 0 ) ? 
            ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok","Thêm bài hát thành công",repository.save(newMusic))
                ): 
            ResponseEntity.status(HttpStatus.FOUND).body(
                new ResponseObject("fail","Tên bài hát đã tồn tại, vui lòng nhập tên khác","")
            );
        } catch (Exception e) {
           return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            new ResponseObject("fail","Lỗi, thêm không thành công",e.getMessage())
        );
        }
    }
    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> updateMusic(@RequestBody Music newMusic , @PathVariable UUID id){
        try {
            int updatedMusic = repository.updatedMusic(newMusic.getMusicName(), newMusic.getIdGenre(), newMusic.getIdSinger(), newMusic.getIsPlayList(),newMusic.getRealeaseTime(), newMusic.getUrlFile(), id); 
        
            if(updatedMusic == 1){
                Music music = repository.findByMusicId(id).get(0);
                return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok","Cập nhật bài hát thành công",music));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject("fail","Cập nhật bài hát thất bại",""));
             
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject("fail","Bài hát không tồn tại",e.getMessage()));
        }
    }
    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteMusic(@PathVariable UUID id){
        try {
            Optional<Music> foundMusic = repository.findById(id);
            if(foundMusic.get().getIsPlayList() == true ){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("fail","Bài hát có trong playlist không thể xóa",""));
            }else{
                repository.deleteById(id);
                return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok","Xóa thành công","")
            );
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject("fail","Không tìm thấy bài hát này",e.getMessage()));
        }
    }
    
}
