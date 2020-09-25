package io.github.mhsscel.bookjavaapi;

import java.time.LocalDateTime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.log4j.Log4j2;

/**
 * Class that starts the application
 * 
 * @author Murillo Henrique
 * @since 03/04/2020 
 */
@Log4j2
@SpringBootApplication
public class BookJavaApiApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(BookJavaApiApplication.class, args);
		log.info("BookJavaAPI started successfully at {}", LocalDateTime.now());
	}

}
