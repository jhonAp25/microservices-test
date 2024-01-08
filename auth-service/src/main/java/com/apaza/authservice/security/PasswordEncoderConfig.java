package com.apaza.authservice.security;


import jakarta.persistence.Entity;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncoderConfig {

    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
