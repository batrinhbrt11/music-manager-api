package com.musicmanager.musicmanger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


import java.util.List;

import com.musicmanager.musicmanger.model.Genre;

import com.musicmanager.musicmanger.repositories.GenreRepository;

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
public class GenreTestController extends  AbstractTest  {
    @Override
    @Before
    public void setUp() {
        super.setUp();

    }
    //private static final Log log = LogFactory.getLog( GenreTestController.class);
    @Test
    @Order(1)
    public void getGenreList() throws Exception {
        String uri = "/api/music-manager/genres?page=1";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
      
        String content = mvcResult.getResponse().getContentAsString();
        JSONObject json = new JSONObject(content);  
        
        Genre[] genrelist = super.mapFromJson(json.getString("content").toString(), Genre[].class);
        assertTrue(genrelist.length > 0);
    }

    @Test 
    @Order(2)
    public void createGenre() throws Exception{
        String uri = "/api/music-manager/genres";
        Genre genre = new Genre();
        genre.setGenreName("genreName");
        String inputJson = super.mapToJson(genre);
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
    private GenreRepository repository;
    public  Genre  getGenreByName() {
        List<Genre> foundGenres = repository.findByGenreName("genreName1");
        return    foundGenres.get(0);
    }
  

    @Test 
    @Order(3)
    public void updateGenre() throws Exception{
        Genre lst = getGenreByName();
        String uri = "/api/music-manager/genres/"+ lst.getGenreId();
        Genre genre = new Genre();
        String newName = "genreName13";
        genre.setGenreName(newName);
        String inputJson = super.mapToJson(genre);
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
        Genre lst = getGenreByName();
        String uri = "/api/music-manager/genres/"+ lst.getGenreId();
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        JSONObject json = new JSONObject(content);
        assertEquals(json .getString("message").toString(), "Deleting successful");
    }
}
