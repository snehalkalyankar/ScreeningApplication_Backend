package com.resumeScreening.service;

import com.resumeScreening.dto.SignUpDto;
import com.resumeScreening.exception.UserNotFoundException;
import com.resumeScreening.model.LoginTable;
import com.resumeScreening.model.SignUpTable;
import com.resumeScreening.model.UserRoles;
import com.resumeScreening.repository.LoginRepository;
import com.resumeScreening.repository.SignUpRepository;
import com.resumeScreening.repository.UserRolesRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    
    

    @Override
    public SignUpTable updatePassword(String currentPassword, String newPassword, String email) throws UserNotFoundException {
        Optional<SignUpTable> user = signUpRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }
        SignUpTable existingUser = user.get();
        LoginTable login = existingUser.getLogin();
        boolean isCurrentPasswordAndExistingPasswordMatches = passwordEncoder.matches(login.getPassword(), currentPassword);
        if (login.getPassword() != null) {
            if (!isCurrentPasswordAndExistingPasswordMatches) {
                throw new RuntimeException("Password does not match");
            }
            login.setPassword(newPassword);
            login.setPassword(passwordEncoder.encode(newPassword));
            existingUser.setLogin(login);
        }
        return signUpRepository.save(existingUser);
    }

	@Override
	@Transactional(rollbackOn = Exception.class)
	public String SaveSignUp(SignUpDto bean) throws Exception {
		// TODO Auto-generated method stub
		try {
        LoginTable login = new LoginTable();
        login.setUserName(bean.getUsername());
        login.setPassword(passwordEncoder.encode(bean.getPassword()));
        UserRoles roles = userRolesRepository.findByRoleCode("002").get();
        login.setRole(roles);
        
        login = loginRepository.save(login);

        // Create a SignUpTable entity
        SignUpTable signUpTable = new SignUpTable();
        signUpTable.setEmail(bean.getEmail());
        signUpTable.setLogin(login);
        
        signUpRepository.save(signUpTable);
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return "SUCCESS";
	}
}
