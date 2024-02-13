package com.resumeScreening;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ScreeningApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScreeningApplication.class, args);
		
		PasswordEncoder encode = new BCryptPasswordEncoder();
		System.out.println( encode.encode("abc123"));
		
	}

	
}
