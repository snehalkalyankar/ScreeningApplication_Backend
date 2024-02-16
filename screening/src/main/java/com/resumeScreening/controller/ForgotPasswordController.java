package com.resumeScreening.controller;

import com.resumeScreening.exception.UserNotFoundException;
import com.resumeScreening.model.SignUpTable;
import com.resumeScreening.service.ForgotPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/auth/password")
public class ForgotPasswordController {
    private final ForgotPasswordService forgotPasswordService;

    @Autowired
    public ForgotPasswordController(ForgotPasswordService forgotPasswordService) {
        this.forgotPasswordService = forgotPasswordService;
    }

    @PostMapping("/forgot-password/{email}")
    public ResponseEntity<?> forgotPassword(@PathVariable String email) throws UserNotFoundException {
        SignUpTable response = forgotPasswordService.forgotPassword(email);

        if (response.getPasswordResetToken().isEmpty()) {
            throw new RuntimeException("Token for Reset is Empty");
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String token, @RequestBody String password) throws UserNotFoundException {
        return new ResponseEntity<>(forgotPasswordService.resetPassword(token, password), HttpStatus.OK);
    }
}
