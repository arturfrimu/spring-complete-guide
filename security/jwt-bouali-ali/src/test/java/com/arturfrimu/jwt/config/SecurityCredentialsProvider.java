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
            @Value("${spring.security.user.name}")
            String username,
            @Value("${spring.security.user.password}")
            String passowrd
    ) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION, "Basic " + HttpHeaders.encodeBasicAuth(username, passowrd, null));
        return headers;
    }
}
