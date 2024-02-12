package com.resumeScreening.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class HomeController {

	@GetMapping("/test")
	public ResponseEntity<?> getHello(){
		return ResponseEntity.ok("Hello, You are Authenticated");
	}
}
