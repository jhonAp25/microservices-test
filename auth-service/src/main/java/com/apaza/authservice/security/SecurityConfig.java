package com.apaza.authservice.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import reactor.core.publisher.Mono;



@EnableWebSecurity
@Configuration

public class SecurityConfig    {

    final static Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
    @Bean
    public SecurityWebFilterChain defaultSecurityFilterChain(ServerHttpSecurity http, AuthenticationManager authenticationManager) {
       /* http.authorizeHttpRequests((authz) -> authz
                        .anyRequest().permitAll());*/

        return http.authorizeExchange()
                .anyExchange().permitAll()
                .and()
                .csrf().disable()
                .build();

        return http.authorizeExchange()
                .pathMatchers("/public/**").permitAll()
                .pathMatchers("/admin/**").hasRole("ADMIN")
                .pathMatchers("/user/**").hasRole("USER")
                .anyExchange().authenticated()
                .and()
                .csrf().disable()
                .build();
    }



    @Bean
    public ServerAuthenticationConverter jwtAuthenticationConverter() {
        return new JwtAuthenticationConverter();
    }    }
}
