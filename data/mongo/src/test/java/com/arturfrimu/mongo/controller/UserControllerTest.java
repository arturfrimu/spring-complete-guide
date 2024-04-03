package com.arturfrimu.mongo.controller;

import com.arturfrimu.mongo.configuration.JsonResourceLoader;
import com.arturfrimu.mongo.document.User;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import static com.arturfrimu.mongo.controller.UserController.GET_USER_BY_ID;
import static lombok.AccessLevel.PRIVATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpMethod.GET;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@FieldDefaults(level = PRIVATE)
class UserControllerTest {

    @Autowired
    TestRestTemplate testRestTemplate;
    @LocalServerPort
    String serverPort;
    String BASE_URL = "http://localhost:";

    @Test
    @SneakyThrows
    void testGetUser() {
        User expected = JsonResourceLoader.load("moks/users/userById.json", User.class);

        User response = getUserById("1");

        assertThat(response)
                .usingRecursiveComparison()
                .isEqualTo(expected);

        System.out.println(expected);
    }

    private User getUserById(final String id) {
        String url = BASE_URL + serverPort + GET_USER_BY_ID.replace("{id}", id);
        ResponseEntity<User> response = testRestTemplate.exchange(url, GET, null, User.class);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();

        return response.getBody();
    }
}