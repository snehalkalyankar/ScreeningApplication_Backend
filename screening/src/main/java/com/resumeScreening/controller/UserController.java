package com.resumeScreening.controller;

import java.util.List;

import com.resumeScreening.bean.PasswordUpdateRequest;
import com.resumeScreening.exception.UserNotFoundException;
import com.resumeScreening.model.SignUpTable;
import com.resumeScreening.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.resumeScreening.model.UserRoles;
import com.resumeScreening.repository.UserRolesRepository;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
    UserRolesRepository userRoleRepository;
	@Autowired
	private UserService userService;
	private Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

	@GetMapping("/test")
	public ResponseEntity<?> getHello(){
		return ResponseEntity.ok("Hello, You are Authenticated");
	}
	
	@GetMapping("/getRoles")
	public ResponseEntity<?> getRoles(){
		List<UserRoles>list = userRoleRepository.findAll();
		
		return ResponseEntity.ok(list);
	}
	@PutMapping("/update-password")
	public ResponseEntity<?> updatePassword(@RequestBody PasswordUpdateRequest request) throws UserNotFoundException {
		logger.debug("API ::: /update-password");
		SignUpTable user = userService.updatePassword(request.getCurrentPassword(), request.getNewPassword(), request.getEmail());
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
}
