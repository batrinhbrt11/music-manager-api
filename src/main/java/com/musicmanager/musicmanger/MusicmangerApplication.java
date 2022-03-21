package com.musicmanager.musicmanger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
@SpringBootApplication
public class MusicmangerApplication {
	private static final Log log = LogFactory.getLog(MusicmangerApplication.class);
    
	public static void main(String[] args) {
		SpringApplication.run(MusicmangerApplication.class, args);
		log.info("Server is running on port 8080");
	}

	@Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowCredentials(true).allowedOriginPatterns("*").allowedMethods("GET","POST","PUT","DELETE").allowedHeaders("*");
            }
        };
    }
}
