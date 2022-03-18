package com.musicmanager.musicmanger.controllers;

import java.util.UUID;

import com.musicmanager.musicmanger.model.Music;
import com.musicmanager.musicmanger.repositories.MusicRepository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;


@Controller
public class PlayListController {
    @Autowired
    private MusicRepository repository;
	private static final Log log = LogFactory.getLog(PlayListController.class);


    @MessageMapping("/addPlaylist") 
    @SendTo("/music/getPlaylist")
    private Music receiveMusic(@Payload UUID id){
       
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
}
