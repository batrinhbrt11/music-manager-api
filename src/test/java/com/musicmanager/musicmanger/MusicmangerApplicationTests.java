package com.musicmanager.musicmanger;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.boot.test.context.SpringBootTest;


import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(Suite.class)
@SuiteClasses({GenreTestController.class, MusicTestController.class})
@SpringBootTest
@WebAppConfiguration
public abstract class MusicmangerApplicationTests {
	
}
