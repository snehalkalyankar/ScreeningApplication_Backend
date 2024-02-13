package com.resumeScreening.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "signup_table")
public class SignUpTable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "signup_id")
	private Long signUpId;
	
	@Column(name = "email")
	private String email;
	
	@OneToOne
	@JoinColumn(name = "login_id")
	@JsonBackReference
	private LoginTable login;

	public Long getSignUpId() {
		return signUpId;
	}

	public void setSignUpId(Long signUpId) {
		this.signUpId = signUpId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LoginTable getLogin() {
		return login;
	}

	public void setLogin(LoginTable login) {
		this.login = login;
	}
	
	
	
}
