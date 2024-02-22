package com.resumeScreening.service;

import com.resumeScreening.bean.ForgotPasswordRequest;
import com.resumeScreening.bean.JWTRequest;
import com.resumeScreening.bean.JWTResponse;
import com.resumeScreening.config.JWTHelper;
import com.resumeScreening.dto.SignUpDto;
import com.resumeScreening.exception.AuthorizationException;
import com.resumeScreening.exception.UserNotFoundException;
import com.resumeScreening.exception.UserSignupException;
import com.resumeScreening.model.LoginTable;
import com.resumeScreening.model.SignUpTable;
import com.resumeScreening.model.UserRoles;
import com.resumeScreening.repository.LoginRepository;
import com.resumeScreening.repository.SignUpRepository;
import com.resumeScreening.repository.UserRolesRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private UserRolesRepository userRolesRepository;

    @Autowired
    private SignUpRepository signUpRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTHelper jwtHelper;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public SignUpTable updatePassword(String currentPassword, String newPassword, String email) throws UserNotFoundException {
        Optional<SignUpTable> user = signUpRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User with this email not Present in Database");
        }
        SignUpTable existingUser = user.get();
        LoginTable login = existingUser.getLogin();

        boolean isCurrentPasswordAndExistingPasswordMatches = passwordEncoder.matches(currentPassword, login.getPassword());
        if (login.getPassword() != null) {
            if (!isCurrentPasswordAndExistingPasswordMatches) {
                throw new UserNotFoundException("Password does not match");
            }
            login.setPassword(newPassword);
            login.setPassword(passwordEncoder.encode(newPassword));
            existingUser.setLogin(login);
        }
        return signUpRepository.save(existingUser);
    }


    @Override
    @Transactional(rollbackOn = Exception.class)
    public String SaveSignUp(SignUpDto bean) throws UserSignupException {
    	
    	 if (!bean.getPassword().equals(bean.getConfirmPassword())) {
             throw new UserSignupException("Passwords do not match please chcek....");
         }

        // TODO Auto-generated method stub

        if (signUpRepository.findByEmail(bean.getEmail()).orElse(null) != null) {
            throw new UserSignupException("User Already Registered!");
        }

//        if (!bean.getUsername().matches("[a-zA-Z0-9]+") || bean.getUsername().length() < 5) {
//            throw new UserSignupException("Username must contain at least 5 alphanumeric characters.");
//        }
        
        if (bean.getPassword().length() < 8) {
            throw new UserSignupException("Password must be at least 8 characters long.");
        }
        if (!bean.getEmail().endsWith("@deloitte.com")) {
            throw new UserSignupException("Email must belong to deloitte.com domain.");
        }
       
        LoginTable login = new LoginTable();
        login.setUserName(bean.getEmail());
        login.setPassword(passwordEncoder.encode(bean.getPassword()));
        UserRoles roles = userRolesRepository.findByRoleCode("002").get();
        login.setRole(roles);

        login = loginRepository.save(login);

        // Create a SignUpTable entity
        SignUpTable signUpTable = new SignUpTable();
        signUpTable.setEmail(bean.getEmail());
        signUpTable.setLogin(login);

        signUpRepository.save(signUpTable);

        return "SUCCESS";
    }


    public JWTResponse validateLogin(JWTRequest request) throws AuthorizationException {
        JWTResponse response = null;
        try {
            this.doAuthenticate(request.getUsername(), request.getPassString());
            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
            String token = this.jwtHelper.generateToken(userDetails);
            response = new JWTResponse(token, userDetails.getUsername());
            response.setEmail(signUpRepository.findByLogin(
            		loginRepository.findByUserName(userDetails.getUsername()).get()
            		).get().getEmail());
            return response;
        } catch (BadCredentialsException e) {
            // TODO: handle exception
            throw new AuthorizationException("Credentials Not Valid!");
        }
    }

    @Override
    public SignUpTable getUser(String email) throws UserNotFoundException {
        Optional<SignUpTable> byEmail = signUpRepository.findByEmail(email);
        if (byEmail.isEmpty()) {
            throw new UserNotFoundException("User not Present in Database");
        }
        return byEmail.get();
    }

    private void doAuthenticate(String email, String password) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        authenticationManager.authenticate(authentication);
    }

    @Override
    public Long generateOtp(String email) throws UserNotFoundException {
        if (Optional.ofNullable(signUpRepository.findByEmail(email)).isEmpty())
            throw new UserNotFoundException("User with this email not Present in Database");
        Random random = new Random();
        return (long) (random.nextInt(900000) + 100000);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void saveOtp(String email, Long otp) throws UserNotFoundException {
        Optional<SignUpTable> userOptional = signUpRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User with this email not Present in Database");
        }

        SignUpTable user = userOptional.get();
        user.setOtp(otp);
        user.setOtpExpirationTime(new Date(System.currentTimeMillis()+5*60*1000));
        signUpRepository.save(user);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public SignUpTable forgotPassword(ForgotPasswordRequest request) throws UserNotFoundException {
        Optional<SignUpTable> userOptional =
                Optional.ofNullable(signUpRepository.findByOtpAndEmail(request.getOtp(), request.getEmail()));

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("Invalid OTP");
        }

        SignUpTable user = userOptional.get();
        LoginTable loginTable = user.getLogin();
        loginTable.setPassword(request.getPassword());
        loginTable.setPassword(passwordEncoder.encode(loginTable.getPassword()));
        user.setLogin(loginTable);
        user.setOtp(null);
        user.setOtpExpirationTime(null);
        signUpRepository.save(user);

        return user;
    }

    @Override
    public String validateOTP(Long otp) throws UserNotFoundException {
        Optional<SignUpTable> userOp = Optional.ofNullable(signUpRepository.findByOtp(otp));

        if (userOp.isEmpty()) {
            throw new UserNotFoundException("Invalid OTP");
        }
        SignUpTable user = userOp.get();

        if (user.getOtpExpirationTime().before(new Date())) {
            user.setOtp(null);
            throw new UserNotFoundException("Your OTP is expired. Create a new OTP if you want to Proceed.");
        }
        return "OTP Validated Successfully.";
    }
}
