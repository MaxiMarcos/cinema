package com.cinema.theater;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TheaterApplication {

	public static void main(String[] args) {
		SpringApplication.run(TheaterApplication.class, args);
	}

}
