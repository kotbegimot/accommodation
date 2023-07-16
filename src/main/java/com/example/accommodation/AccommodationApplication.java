package com.example.accommodation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication

@ConfigurationPropertiesScan("com.example.accommodation.util")
public class AccommodationApplication {
	public static void main(String[] args) {
		SpringApplication.run(AccommodationApplication.class, args);
	}
}
