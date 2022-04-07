package com.musicmanager.musicmanger;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


import java.util.List;

import com.musicmanager.musicmanger.model.Singer;


import com.musicmanager.musicmanger.repositories.SingerRepository;

// import org.apache.commons.logging.Log;
// import org.apache.commons.logging.LogFactory;
import org.json.*;

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
public class SingerTestController extends  AbstractTest{
    @Override
    @Before
    public void setUp() {
        super.setUp();

    }
    @Test
    @Order(1)
    public void getSingerList() throws Exception {
        String uri = "/api/music-manager/singers?page=1";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
      
        String content = mvcResult.getResponse().getContentAsString();
        JSONObject json = new JSONObject(content);  
       
        Singer[] singerlist = super.mapFromJson(json.getString("content").toString(),  Singer[].class);
        System.out.println(singerlist);
        assertTrue( singerlist.length > 0);
    }
    @Test
    @Order(2)
    public void createSinger() throws Exception{
        String uri = "/api/music-manager/singers";
        Singer singer = new Singer("eee");
        String inputJson = super.mapToJson(singer);
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
    private SingerRepository repository;
    public  Singer  getSingerByName() {
        List<Singer> foundGenres = repository.findBySingerName("ádasdasd");
        return    foundGenres.get(0);
    }

    @Test 
    @Order(3)
    public void updateGenre() throws Exception{
        Singer lst = getSingerByName();
        String uri = "/api/music-manager/singers/"+ lst.getSingerId();
        Singer singer = new Singer();
        String newName = "avcde";
        singer.setSingerName(newName);
        String inputJson = super.mapToJson(singer);
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
    public void deleteGenre() throws Exception{
        Singer lst = getSingerByName();
        String uri = "/api/music-manager/singers/"+ lst.getSingerId();
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        JSONObject json = new JSONObject(content);
        assertEquals(json .getString("message").toString(), "Deleting successful");
    }
}
