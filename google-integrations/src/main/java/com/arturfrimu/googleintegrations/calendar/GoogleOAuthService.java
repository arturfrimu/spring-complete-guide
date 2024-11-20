package com.arturfrimu.googleintegrations.calendar;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

@Service
public class GoogleOAuthService {

    @Value("${google.client.id}")
    private String clientId;

    @Value("${google.client.secret}")
    private String clientSecret;

    @Value("${google.redirect.uri}")
    private String redirectUri;

    @Value("${google.auth.url}")
    private String authUrl;

    @Value("${google.token.url}")
    private String tokenUrl;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void redirectToGoogleAuth(HttpServletResponse response) throws IOException {
        String redirectUrl = UriComponentsBuilder.fromHttpUrl(authUrl)
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("response_type", "code")
                .queryParam("include_granted_scopes", "true")
                .queryParam("state", "hellothere")
                .queryParam("scope", "https://www.googleapis.com/auth/calendar")
                .queryParam("access_type", "offline")
                .toUriString();
        response.sendRedirect(redirectUrl);
    }

    @SneakyThrows
    public ResponseEntity<String> exchangeCodeForTokens(String code) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", code);
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("redirect_uri", redirectUri);
        body.add("grant_type", "authorization_code");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        ResponseEntity<String> response = restTemplate.postForEntity(tokenUrl, new org.springframework.http.HttpEntity<>(body, headers), String.class);

        File file = new File("C:\\Users\\Agent\\Desktop\\projects\\spring-complete-guide\\google-integrations\\src\\main\\resources\\credentials.json");
        if (!file.exists()) {
            file.createNewFile();
        }

        OAuthTokenDto value = objectMapper.readValue(response.getBody(), OAuthTokenDto.class);
        objectMapper.writeValue(file, value);

        return response;
    }

    @SneakyThrows
    public ResponseEntity<String> refreshAccessToken() {
        File file = new File("C:\\Users\\Agent\\Desktop\\projects\\spring-complete-guide\\google-integrations\\src\\main\\resources\\credentials.json");
        OAuthTokenDto oAuthTokenDto = objectMapper.readValue(file, OAuthTokenDto.class);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("refresh_token", oAuthTokenDto.getRefreshToken());
        body.add("grant_type", "refresh_token");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        ResponseEntity<String> response = restTemplate.postForEntity(tokenUrl, new org.springframework.http.HttpEntity<>(body, headers), String.class);

        File fileForAccessToken = new File("C:\\Users\\Agent\\Desktop\\projects\\spring-complete-guide\\google-integrations\\src\\main\\resources\\access_token.json");
        if (!file.exists()) {
            file.createNewFile();
        }

        OAuthTokenDto value = objectMapper.readValue(response.getBody(), OAuthTokenDto.class);
        objectMapper.writeValue(fileForAccessToken, value);

        return response;
    }

    @SneakyThrows
    public ResponseEntity<String> createEvent(String eventData) {
        File file = new File("C:\\Users\\Agent\\Desktop\\projects\\spring-complete-guide\\google-integrations\\src\\main\\resources\\access_token.json");
        OAuthTokenDto oAuthTokenDto = objectMapper.readValue(file, OAuthTokenDto.class);

        createGoogleCalendarEvent(oAuthTokenDto.getAccessToken(), eventData);

        return ResponseEntity.ok().build();
    }

    public void createGoogleCalendarEvent(String accessToken, String eventData) {
        String apiUrl = "https://www.googleapis.com/calendar/v3/calendars/primary/events";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> request = new HttpEntity<>(eventData, headers);
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, request, String.class);

        if (response.getStatusCode() == HttpStatus.OK || response.getStatusCode() == HttpStatus.CREATED) {
            System.out.println("Event created successfully!");
        } else {
            System.out.printf("Failed to create event. HTTP response code: %s%n", response.getStatusCode());
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OAuthTokenDto implements Serializable {
        @JsonProperty("access_token")
        private String accessToken;
        @JsonProperty("expires_in")
        private int expiresIn;
        @JsonProperty("refresh_token")
        private String refreshToken;
        @JsonProperty("scope")
        private String scope;
        @JsonProperty("token_type")
        private String tokenType;
    }
}
