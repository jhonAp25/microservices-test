package com.apaza.authservice.controller;


import com.apaza.authservice.dto.AuthUserDTO;
import com.apaza.authservice.dto.TokenDTO;
import com.apaza.authservice.entity.AuthUser;
import com.apaza.authservice.service.AuthUserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthUserController {


    @Autowired
    AuthUserService authUserService;


    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody AuthUserDTO userDTO){

        TokenDTO tokenDTO = authUserService.login(userDTO);

        if (tokenDTO == null)
            return  ResponseEntity.badRequest().build();
        return ResponseEntity.ok(tokenDTO);

    }

    @PostMapping("/validate")
    public ResponseEntity<TokenDTO> validate(@RequestParam String token){
        TokenDTO tokenDTO = authUserService.validate(token);
        if(tokenDTO==null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(tokenDTO);
    }


    @PostMapping("/create")
    public ResponseEntity<AuthUser> create (@RequestBody AuthUserDTO userDTO){
        AuthUser authUser = authUserService.save(userDTO);
        if (authUser==null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(authUser);
    }


}
