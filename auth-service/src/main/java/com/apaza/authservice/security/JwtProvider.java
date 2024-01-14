package com.apaza.authservice.security;


import com.apaza.authservice.entity.AuthUser;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Component
public class JwtProvider {


    @Value("${jwt.secret}")
    private String secret;


  /*  @PostConstruct
    protected void init(){
        System.out.println("El método init() se está llamando...");
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }
*/
    private Key getKey() {
        byte[] keyBytes= Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }



    public  String createToken (AuthUser authUser){


        Map<String , Object> claims = new HashMap<>();
        claims = Jwts.claims().setSubject(authUser.getUserName());
        claims.put("id", authUser.getId());
        claims.put("name", authUser.getUserName());
        Date now = new Date();
        Date exp = new Date(now.getTime() + 3600000 ) ;

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(authUser.getUserName())
                .setIssuedAt(now)
                .setExpiration(exp)
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    public boolean validate (String token ){

        try {
            Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJwt(token);
            return true;

        }catch (Exception e){
            return false;
        }

    }

    public String getUserNameFromToken(String token ){

        try {
            return Jwts.parser().setSigningKey(getKey()).parseClaimsJws(token).getBody().getSubject();
        }catch (Exception e ){
            return "bad token";
        }
    }


}
