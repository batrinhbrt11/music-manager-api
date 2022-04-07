package com.musicmanager.musicmanger.controllers;

import java.io.IOException;
import java.util.ArrayList;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.musicmanager.musicmanger.Pagination;
import com.musicmanager.musicmanger.model.Genre;
import com.musicmanager.musicmanger.model.Music;
import com.musicmanager.musicmanger.model.ResponseObject;
import com.musicmanager.musicmanger.model.Singer;
import com.musicmanager.musicmanger.repositories.GenreRepository;
import com.musicmanager.musicmanger.repositories.MusicRepository;
import com.musicmanager.musicmanger.repositories.SingerRepository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


@RestController
@RequestMapping(path = "api/music-manager/musics")
public class MusicController {
   private static final Log log = LogFactory.getLog(MusicController.class);
    private static final int PAGE_SIZE = 10;
    @Autowired
    private MusicRepository repository;
 
    @GetMapping("/getAll")
    public List<Music> getAllMusic() {
        ArrayList<Music> musics = (ArrayList<Music>) repository.findAll();
        Comparator<Music> compareCreateDate = (Music m1, Music m2) -> m1.getCreated().compareTo(m2.getCreated());
        Collections.sort(musics, compareCreateDate.reversed());
        
        return musics;
    }

    @GetMapping("")
    ResponseEntity<ResponseObject> musicPageable(@RequestParam int page) {
        Pageable paging = PageRequest.of(page - 1, PAGE_SIZE, Sort.by("created").descending());
        PageImpl pageImpl = (PageImpl) repository.findAll(paging);
 
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "",pageImpl.getContent(), pageImpl.getTotalElements()));
    }

    @GetMapping("/genre/{idGenre}")
    ResponseEntity<ResponseObject> getMusicByGenre(@PathVariable UUID idGenre,@RequestParam int page){
        
        ArrayList<Music> musics = (ArrayList<Music>) repository.findAll().stream().filter(m-> m.getIdGenre().equals(idGenre) ).collect(Collectors.toList());
        Comparator<Music> compareCreateDate = (Music m1, Music m2) -> m1.getCreated().compareTo(m2.getCreated());
        Collections.sort(musics, compareCreateDate.reversed());
        Pagination paging = new Pagination(page,musics);
    
        return ResponseEntity.status(HttpStatus.OK).body(
            new ResponseObject("ok", "List", paging.paginationsList(),musics.size()));
    }
    
    @GetMapping("/singer/{idSinger}")
    ResponseEntity<ResponseObject> getMusicBySinger(@PathVariable UUID idSinger,@RequestParam int page){
        
        ArrayList<Music> musics = (ArrayList<Music>) repository.findAll().stream().filter(m-> m.getIdSinger().equals(idSinger) ).collect(Collectors.toList());
        Comparator<Music> compareCreateDate = (Music m1, Music m2) -> m1.getCreated().compareTo(m2.getCreated());
        Collections.sort(musics, compareCreateDate.reversed());
        Pagination paging = new Pagination(page,musics);
        return ResponseEntity.status(HttpStatus.OK).body(
            new ResponseObject("ok", "List", paging.paginationsList(),musics.size()));
    }
    
    @GetMapping("/playlist")
    ResponseEntity<ResponseObject> getPlaylist(@RequestParam int page){
        
        ArrayList<Music> musics = (ArrayList<Music>) repository.findAll().stream().filter(m-> m.getIsPlayList() ).collect(Collectors.toList());
        Comparator<Music> compareCreateDate = (Music m1, Music m2) -> m1.getCreated().compareTo(m2.getCreated());
        Collections.sort(musics, compareCreateDate.reversed());
        Pagination paging = new Pagination(page,musics);
        return ResponseEntity.status(HttpStatus.OK).body(
            new ResponseObject("ok", "List", paging.paginationsList(),musics.size()));
    }

    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable UUID id) {
        List<Music> foundMusic = repository.findByMusicId(id);
        return (foundMusic.size() > 0) ? ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "", foundMusic))
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("fail", "Music not found", ""));
    }

    @PostMapping("")
    ResponseEntity<ResponseObject> insertMusic(@RequestBody Music newMusic) {
        List<Music> foundMusic = repository.findByMusicName(newMusic.getMusicName().trim());
        try {
            return (foundMusic.size() == 0) ? ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Adding successful", repository.save(newMusic)))
                    : ResponseEntity.status(HttpStatus.FOUND).body(
                            new ResponseObject("fail", "Music is existed, Enter new name", ""));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("fail", "Error, Can not add music", e.getMessage()));
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
                new ResponseObject("ok", "Update success", updatedMusic));
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject("fail", "Error, cannot update", ""));
        }
    }

    @MessageMapping("/addPlaylist") 
    @SendTo("/music/getPlaylist")
    @PutMapping("/playlist/{id}")
    Music addPlaylist(@PathVariable UUID id){
        Music updatedMusic = repository.findById(id).map(music->{
            music.setPlayList(!music.getIsPlayList());
            return repository.save(music);
        }).orElseGet(()->{
            return null;
        });
        if(updatedMusic != null){
            
            return  updatedMusic;
        }else{
            return null;
        }
    }
    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteMusic(@PathVariable UUID id) {
        try {
            Optional<Music> foundMusic = repository.findById(id);
            if (foundMusic.get().getIsPlayList() == true) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new ResponseObject("fail", "Music is exited in Playlist, cannot remove ", ""));
            } else {
                repository.deleteById(id);
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Deleting success", ""));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("fail", "Cannot find music", e.getMessage()));
        }
    }

    @GetMapping("/search")
    ResponseEntity<ResponseObject> getMusicBySinger(@RequestParam String text){
        ArrayList<Music> musics = (ArrayList<Music>) repository.findAll().stream().filter(m-> m.getMusicName().contains(text) ).collect(Collectors.toList());
        Comparator<Music> compareCreateDate = (Music m1, Music m2) -> m1.getCreated().compareTo(m2.getCreated());
        Collections.sort(musics, compareCreateDate.reversed());
        int endItem=10;
        if(musics.size()<10){
            endItem  = musics.size();
        }
        return ResponseEntity.status(HttpStatus.OK).body(
            new ResponseObject("ok", "List",musics.subList(0, endItem)));
    }
    @Autowired
    private GenreRepository genreRepository;
    @Autowired 
    private SingerRepository singerRepository;
    @PostMapping("/insert-from-link")
    ResponseEntity<ResponseObject> insertMusicFromLink(@RequestParam String url) throws IOException {
        try{
            Document doc = Jsoup.connect(url).timeout(5000).get();
            Element name = doc.select("h1[class=xtitle-name]").first();
            Element page = doc.select("span[class=mr20]").last();
            Elements genre = page.getElementsByTag("a");
            page = doc.select("div[class=play-about]").first();
            page = page.getElementsByClass("xtitle").first();
            Element singer = page.getElementsByTag("a").last();
            Element audio = doc.getElementsByTag("audio").first();
            List<Music> foundMusic = repository.findByMusicName(name.ownText().trim());
            if(foundMusic.size() ==0 ){
                UUID idGenre = null;
                if(genre.text().isEmpty()){
                    idGenre = getIdGenre("Unknown");
                }else{
                    idGenre= getIdGenre(genre.text());
                }
                UUID idSinger  = null;
                if(genre.text().isEmpty()){
                    idSinger  = getIdSinger("Unknown");
                }else{
                    idSinger = getIdSinger(singer.text());
                }
          
                Music newMusic = new Music(name.ownText(),idGenre, idSinger,false,null,audio.absUrl("src"));
                return ResponseEntity.status(HttpStatus.FOUND).body(
                    new ResponseObject("ok", "Adding success",repository.save(newMusic)));
            }
        
            return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("fail", "Music is existed, Enter new name",""));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("fail", "Link not found",""));
        }
       
    }

    UUID getIdGenre(String genreName){
        if( genreName.isEmpty() ){
            return null;
        }
            List<Genre> foundGenres = genreRepository.findByGenreName(genreName.trim());
            if(foundGenres.size() ==0){
                Genre newGenre = new Genre(genreName);
                return genreRepository.save(newGenre).getGenreId();
            }
            return foundGenres.get(0).getGenreId();
        
       
    }
    UUID getIdSinger(String singerName){
        if( singerName.isEmpty()){
            return null;
        }
        List<Singer> foundSinger = singerRepository.findBySingerName(singerName.trim());
        if(foundSinger.size() ==0){
            Singer newSinger = new Singer(singerName);
            return singerRepository.save(newSinger).getSingerId();
        }
        return foundSinger.get(0).getSingerId();
    }
}
