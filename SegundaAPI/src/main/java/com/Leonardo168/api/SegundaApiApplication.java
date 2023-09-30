package com.Leonardo168.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Leonardo168.api.models.ParkingSpotModel;

@SpringBootApplication
@RestController
public class SegundaApiApplication {

	public static void main(String[] args) {
		//SpringApplication.run(SegundaApiApplication.class, args);
	}
	
	@GetMapping("/")
	public String index() {
		return "Olá Mundo!";
	}

}
