package com.apaza.authservice.service;


import com.apaza.authservice.dto.AuthUserDTO;
import com.apaza.authservice.dto.TokenDTO;
import com.apaza.authservice.dto.UserDTO;
import com.apaza.authservice.entity.AuthUser;
import com.apaza.authservice.entity.Role;
import com.apaza.authservice.repository.AuthUserRepository;
import com.apaza.authservice.security.JwtProvider;
import com.apaza.authservice.security.PasswordEncoderConfig;
import com.netflix.discovery.converters.Auto;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.cert.Extension;
import java.util.List;
import java.util.Optional;

@Service
public class AuthUserService {


    @Autowired
    AuthUserRepository authUserRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtProvider jwtProvider;



    public AuthUser save (UserDTO userDTO){
        Optional<AuthUser> user = authUserRepository.findByUsername(userDTO.getUsername());
        if(user.isPresent())
            throw new RuntimeException("No se puede duplicar el USERNAME");
        String password = passwordEncoder.encode(userDTO.getPassword());
        String userText = userDTO.getUsername();
        AuthUser authUser = AuthUser.builder()
                .username(userText)
                .password(password)
                .role(Role.valueOf(userDTO.getRol()))
                .build();

        return authUserRepository.save(authUser);
    }


    public TokenDTO login(AuthUserDTO userDTO){
        Optional<AuthUser> user = authUserRepository.findByUsername(userDTO.getUsername());
        if(!user.isPresent())
            return null;
        if (passwordEncoder.matches(userDTO.getPassword(), user.get().getPassword()))
            return new TokenDTO(jwtProvider.createToken(user.get()));
        return null;

    }

    public TokenDTO validate (String token){
        if (!jwtProvider.validate(token))
            return null;
        String username= jwtProvider.getUserNameFromToken(token);
        if(!authUserRepository.findByUsername(username).isPresent())
            return null;
        return new TokenDTO(token);
    }

    public List<AuthUser> listUsers (){
        return authUserRepository.findAll();
    }
}
