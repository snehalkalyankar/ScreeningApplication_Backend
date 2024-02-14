package com.resumeScreening.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.resumeScreening.bean.JWTRequest;
import com.resumeScreening.bean.JWTResponse;
import com.resumeScreening.bean.PasswordUpdateRequest;
import com.resumeScreening.config.JWTHelper;
import com.resumeScreening.dto.SignUpDto;
import com.resumeScreening.exception.UserNotFoundException;
import com.resumeScreening.model.LoginTable;
import com.resumeScreening.model.SignUpTable;
import com.resumeScreening.model.UserRoles;
import com.resumeScreening.repository.LoginRepository;
import com.resumeScreening.repository.SignUpRepository;
import com.resumeScreening.repository.UserRolesRepository;
import com.resumeScreening.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTHelper jwtHelper;
    



    private Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @PostMapping("/login")
    public ResponseEntity<JWTResponse> login(@RequestBody JWTRequest request) {

        this.doAuthenticate(request.getUsername(), request.getPassString());
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = this.jwtHelper.generateToken(userDetails);
        JWTResponse response = new JWTResponse(token, userDetails.getUsername());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update-password")
    public ResponseEntity<?> updatePassword(@RequestBody PasswordUpdateRequest request) {
        try {
            SignUpTable user = userService.updatePassword(request.getCurrentPassword(), request.getNewPassword(), request.getEmail());
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void doAuthenticate(String email, String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            authenticationManager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }
    }

    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }

    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@RequestBody SignUpDto bean) {
    	String status;
        try {
            // Create a LoginTable entity
        	status = userService.SaveSignUp(bean);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Failed to sign up");
        }
        return ResponseEntity.ok(status);
    }
    

}
