package com.example.umerautos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@EnableCaching
public class UmerautosApplication {

	public static void main(String[] args) {
		SpringApplication.run(UmerautosApplication.class, args);
	}


}
