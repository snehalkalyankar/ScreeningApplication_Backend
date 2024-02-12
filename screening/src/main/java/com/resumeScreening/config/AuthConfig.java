package com.resumeScreening.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class AuthConfig {
	
	@Bean
	public UserDetailsService userDetailsService() {
		 UserDetails user1 = User.builder().username("SamrSawant").password(passwordEncoder().encode("abc")).roles("USER").build();
		 UserDetails user2 = User.builder().username("snkalyankar").password(passwordEncoder().encode("abc")).roles("USER").build();
		 UserDetails user3 = User.builder().username("shwlandge").password(passwordEncoder().encode("abc")).roles("USER").build();
		 
		 return new InMemoryUserDetailsManager(user1,user2,user3);
	}
	
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}


	    @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }	
	
}
