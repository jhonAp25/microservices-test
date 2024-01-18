package com.apaza.gatewayservice.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {




    @Bean
    @LoadBalanced
    public WebClient.Builder builder(){

        return WebClient.builder();
    }
}
