package com.musicmanager.musicmanger.controllers;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.musicmanager.musicmanger.model.Music;
import com.musicmanager.musicmanger.model.ResponseObject;
import com.musicmanager.musicmanger.model.Singer;
import com.musicmanager.musicmanger.repositories.MusicRepository;
import com.musicmanager.musicmanger.repositories.SingerRepository;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
//@CrossOrigin(origins= "*")
@RequestMapping(path = "api/music-manager/singers")
public class SingerController {
    @Autowired
    private SingerRepository repository;

    @GetMapping("/getAll")
    List<Singer> getAllGenre() {
        return repository.findAll();
    }

    private static final int PAGE_SIZE = 10;

    @GetMapping("")
    ResponseEntity<ResponseObject> singerPageable(@RequestParam int page) {
        Pageable paging = PageRequest.of(page - 1, PAGE_SIZE, Sort.by("created").descending());
        JSONArray arr = new JSONArray();
        for (Singer singer : repository.findAll(paging).getContent()) {
            List<Music> musics = musicRepository.findAll().stream()
                    .filter(m -> m.getIdSinger().equals(singer.getSingerId())).collect(Collectors.toList());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("singerId", singer.getSingerId());
            jsonObject.put("singerName", singer.getSingerName());
            jsonObject.put("singerBirthDay", singer.getSingerBirthdy());
            jsonObject.put("singerSex", singer.isSingerSex());
            jsonObject.put("description", singer.getDescription());
            jsonObject.put("urlImage", singer.getUrlImage());
            jsonObject.put("count", musics.size());
            arr.add(jsonObject);

        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "", arr, repository.findAll(paging).getTotalElements()));
    }

    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable UUID id) {
        List<Singer> foundSingers = repository.findBySingerId(id);
        return (foundSingers.size() > 0) ? ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Singer", foundSingers))
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("fail", "Singer not found", ""));
    }

    @PostMapping("")
    ResponseEntity<ResponseObject> insertMusic(@RequestBody Singer newSinger) {
        List<Singer> foundSingers = repository.findBySingerName(newSinger.getSingerName().trim());
        return (foundSingers.size() == 0) ? ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Adding successful", repository.save(newSinger)))
                : ResponseEntity.status(HttpStatus.FOUND).body(
                        new ResponseObject("fail", "Error, singer is existed", ""));

    }

    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> updateSinger(@RequestBody Singer newSinger, @PathVariable UUID id) {
        Singer updatedSinger = repository.findById(id)
                                .map(singer->{
                                    singer.setSingerName(newSinger.getSingerName());
                                    singer.setSingerBirthdy(newSinger.getSingerBirthdy());
                                    singer.setSingerSex(newSinger.isSingerSex());
                                    singer.setDescription(newSinger.getDescription());
                                    singer.setUrlImage(newSinger.getUrlImage());
                                    return repository.save(singer);
                                }).orElseGet(() -> {
                    return null;
                });
        if (updatedSinger != null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Update successful", updatedSinger));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("fail", "Error, can not update", ""));
        }
    }
    @Autowired
    private MusicRepository musicRepository;

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteSinger(@PathVariable UUID id) {
        boolean exists = repository.existsById(id);
        List<Music> musics = musicRepository.findAll().stream().filter(m -> m.getIdGenre().equals(id))
                .collect(Collectors.toList());
        ;

        if (exists) {
            if (musics.size() > 0) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("fail", "Error, singer have musics, can not remove", ""));
            } else {
                repository.deleteById(id);
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Deleting successful", ""));
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("fail", "Error", ""));
    }
}
