package com.example.yatraniwas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class YatraniwasApplication {

	public static void main(String[] args) {
		SpringApplication.run(YatraniwasApplication.class, args);
	}

}
