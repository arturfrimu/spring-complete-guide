package com.arturfrimu.jwt.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class DemoControllerTest {

    @LocalServerPort
    private Integer localServerPort;

    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private HttpHeaders basicSecurityHeaders;

    @Test
    void sayHello() {
        URI uri = UriComponentsBuilder
                .newInstance()
                .scheme("http")
                .host("localhost")
                .port(localServerPort)
                .path("/api/v1/demo-controller")
                .build()
                .toUri();

        RequestEntity<Void> requestEntity = RequestEntity.get(uri)
                .headers(basicSecurityHeaders)
                .build();

        ResponseEntity<String> response = testRestTemplate.exchange(requestEntity, sayHelloResponseType);

        Assertions.assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        Assertions.assertThat(response.getBody()).isEqualTo("Hello from secured endpoint");
    }

    //@formatter:off
    private static final ParameterizedTypeReference<String> sayHelloResponseType = new ParameterizedTypeReference<>() {};
}