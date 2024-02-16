package com.resumeScreening.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "signup_table")
public class SignUpTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "signup_id")
    private Long signUpId;

    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "otp")
    private Long otp;
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

    public Long getOtp() {
        return otp;
    }

    public void setOtp(Long otp) {
        this.otp = otp;
    }
}
