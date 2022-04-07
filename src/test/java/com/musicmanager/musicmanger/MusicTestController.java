package com.musicmanager.musicmanger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.musicmanager.musicmanger.model.Music;
import com.musicmanager.musicmanger.repositories.MusicRepository;


import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
public class MusicTestController extends  AbstractTest  {
    @Override
    @Before
    public void setUp() {
        super.setUp();

    }

    @Test
    @Order(1)
    public void getMusicList() throws Exception {
        String uri = "/api/music-manager/musics?page=1";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        JSONObject json = new JSONObject(content);  
        
        Music[] genrelist = super.mapFromJson(json.getString("content").toString(), Music[].class);
        assertTrue(genrelist.length > 0);
    }
    @Test
    @Order(2)
    public void createMusic() throws Exception{
        String uri = "/api/music-manager/musics";
        Date date = new Date();
        UUID idgenre = UUID.fromString("237244c2-4509-4a4d-af6a-aa00ed4632c5");
        UUID idsinger = UUID.fromString("bc145530-1822-435a-8bed-b53cb7f23973");
        
        Music music = new Music("name", idgenre, idsinger, false, date, "urlFile");
        String inputJson = super.mapToJson(music);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
         .contentType(MediaType.APPLICATION_JSON_VALUE)
         .content(inputJson)).andReturn();
         int status = mvcResult.getResponse().getStatus();
         assertEquals(200, status);
         String content = mvcResult.getResponse().getContentAsString();
         JSONObject json = new JSONObject(content);
         assertEquals(json .getString("message").toString(), "Adding successful");
    }
    @Autowired
    private MusicRepository repository;
    public  Music getMusicByName() {
        List<Music> foundMusics = repository.findByMusicName("Polaroid");
        return    foundMusics.get(0);
    }
  
    @Test
    @Order(3)
    public void updateMusic() throws Exception{
        Music foundMusic = getMusicByName();
       
        String uri=  "/api/music-manager/musics/" + foundMusic.getMusicId();
        String newName = "Unknown1";
        foundMusic.setMusicName(newName);
        String inputJson = super.mapToJson(foundMusic);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        JSONObject json = new JSONObject(content);
        
        assertTrue(json .getString("content").toString().contains(newName));
    }

    @Test 
    @Order(4)
    public void deleteMusic() throws Exception{
        Music foundMusic = getMusicByName();
        String uri=  "/api/music-manager/musics/" + foundMusic.getMusicId();
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        JSONObject json = new JSONObject(content);
        assertEquals(json .getString("message").toString(), "Deleting success");
    }
}
