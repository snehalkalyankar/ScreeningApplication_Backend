package com.resumeScreening.controller;

import com.google.gson.Gson;
import com.resumeScreening.bean.JWTRequest;
import com.resumeScreening.bean.JWTResponse;
import com.resumeScreening.bean.PasswordUpdateRequest;
import com.resumeScreening.dto.EmailDetailsDto;
import com.resumeScreening.dto.SignUpDto;
import com.resumeScreening.exception.UserNotFoundException;
import com.resumeScreening.model.SignUpTable;
import com.resumeScreening.service.EmailService;
import com.resumeScreening.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private static final Gson gson = new Gson();
    @Autowired
    private UserService userService;
    @Autowired
    private EmailService senderService;


    private Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody JWTRequest request) {
        JWTResponse response = null;
        logger.debug("API ::: /login");
        response = userService.validateLogin(request);
       
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update-password")
    public ResponseEntity<?> updatePassword(@RequestBody PasswordUpdateRequest request) {
    	logger.debug("API ::: /update-password");
        try {
            SignUpTable user = userService.updatePassword(request.getCurrentPassword(), request.getNewPassword(), request.getEmail());
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@RequestBody SignUpDto bean) {
        String status = null;
        logger.debug("API ::: /signUp");
        status = userService.SaveSignUp(bean);

        return ResponseEntity.ok(status);
    }

    @PutMapping("/sentOTP/{email}")
    public ResponseEntity<?> sentOTPForForgotUserPassword(@PathVariable String email) {
        try {
            //generate OTP
            Long otp = userService.generateOtp(email);
            System.out.println("Otp generated");

            // Save the OTP to the database
            userService.saveOtp(email, otp);
            System.out.println("Otp saved to database");

            // Email the user with the OTP
            SignUpTable user = userService.getUser(email);
            EmailDetailsDto details = getEmailDetails(email, user, otp);
            senderService.sendEmail(details);
            String message = "Mail sent successfully";
            System.out.println(message);
        logger.debug("API ::: /forgot-password");
            return new ResponseEntity<>(gson.toJson(message), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private static EmailDetailsDto getEmailDetails(String email, SignUpTable user, Long otp) {
        String content = "Dear " + user.getLogin().getUsername() + ", \n\n"
                + "For security reason, you're required to use the following "
                + "One Time Password to login: "
                + otp
                + "\nNote: this OTP is set to expire in 5 minutes."
                + "\n\nRegards"
                + "\nKanban Team";

        String subject = "Here's your One Time Password (OTP) - Expire in 5 minutes!";

        EmailDetailsDto details = new EmailDetailsDto();
        details.setRecipient(email);
        details.setMsgBody(content);
        details.setSubject(subject);
        return details;
    }

    @PutMapping("/forgot-password")
    public ResponseEntity<?> forgotUserPassword(@RequestParam Long otp, @RequestParam String password) throws UserNotFoundException {
        return new ResponseEntity<>(userService.forgotPassword(otp, password), HttpStatus.OK);
    }

    @GetMapping("/validate-otp/{otp}")
    public ResponseEntity<?> validateOtp(@PathVariable Long otp) throws UserNotFoundException {
        return new ResponseEntity<>(gson.toJson(userService.validateOTP(otp)), HttpStatus.OK);
    }
}
