package com.example.accommodation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@EnableCaching
@ConfigurationPropertiesScan("com.example.accommodation.properties")
public class AccommodationApplication {
	public static void main(String[] args) {
		SpringApplication.run(AccommodationApplication.class, args);
	}
}
