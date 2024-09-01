package com.arturfrimu.jwt.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Configuration
public class SecurityCredentialsProvider {

    @Bean
    public HttpHeaders basicSecurityHeaders(
            @Value("${spring.security.user.name}") String username,
            @Value("${spring.security.user.password}") String password
    ) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION, "Basic " + HttpHeaders.encodeBasicAuth(username, password, null));
        return headers;
    }

    @Bean
    public HttpHeaders bearerSecurityHeaders(@Value("${spring.security.user.token}") String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        return headers;
    }
}
