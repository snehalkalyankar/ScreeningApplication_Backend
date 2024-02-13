package com.resumeScreening.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.resumeScreening.model.UserRoles;
import com.resumeScreening.repository.UserRolesRepository;

@RestController
@RequestMapping("/home")
public class HomeController {
	
	@Autowired UserRolesRepository userRoleRepository;

	@GetMapping("/test")
	public ResponseEntity<?> getHello(){
		return ResponseEntity.ok("Hello, You are Authenticated");
	}
	
	@GetMapping("/getRoles")
	public ResponseEntity<?> getRoles(){
		List<UserRoles>list = userRoleRepository.findAll();
		
		return ResponseEntity.ok(list);
	}
}
