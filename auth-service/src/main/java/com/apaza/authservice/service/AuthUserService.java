package com.apaza.authservice.service;


import com.apaza.authservice.dto.AuthUserDTO;
import com.apaza.authservice.dto.TokenDTO;
import com.apaza.authservice.entity.AuthUser;
import com.apaza.authservice.repository.AuthUserRepository;
import com.apaza.authservice.security.JwtProvider;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthUserService {


    @Autowired
    AuthUserRepository authUserRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtProvider jwtProvider;

    public AuthUser save (AuthUserDTO userDTO){
        Optional<AuthUser> user = authUserRepository.findByUserName(userDTO.getUserName());
        if(user.isPresent())
            return null;
        String password = passwordEncoder.encode(userDTO.getPassword());
        AuthUser authUser = AuthUser.builder()
                .userName(userDTO.getUserName())
                .password(password)
                .build();

        return authUserRepository.save(authUser);
    }

    public TokenDTO login(AuthUserDTO userDTO){
        Optional<AuthUser> user = authUserRepository.findByUserName(userDTO.getUserName());
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
        if(!authUserRepository.findByUserName(username).isPresent())
            return null;
        return new TokenDTO(token);
    }
}
