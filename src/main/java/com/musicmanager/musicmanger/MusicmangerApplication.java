package com.musicmanager.musicmanger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
@SpringBootApplication
public class MusicmangerApplication {
	private static final Log log = LogFactory.getLog(MusicmangerApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(MusicmangerApplication.class, args);
		log.info("Server is running on port 8080");
	}

}
