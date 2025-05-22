package com.arturfrimu.nomenclatures.third;


import com.arturfrimu.nomenclatures.third.dto.ArticleView;
import com.arturfrimu.nomenclatures.third.dto.UserView;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NomControllerTest {

    private static final String CREATE = "/nomenclature";

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int serverPort;

    @Nested
    class Create {
        @Test
        void createUser() {
            ResponseEntity<UserView> response = testRestTemplate.exchange(
                    RequestEntity.post(URI.create(buildPath(CREATE)))
                            .body(new UserView(Long.parseLong(RandomStringUtils.randomNumeric(6)), "name", "surname")),
                    UserView.class
            );

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
            assertThat(response.getBody().getName()).isEqualTo("name");
            assertThat(response.getBody().getSurname()).isEqualTo("surname");
        }

        @Test
        void createArticle() {
            ResponseEntity<ArticleView> response = testRestTemplate.exchange(
                    RequestEntity.post(URI.create(buildPath(CREATE)))
                            .body(new ArticleView(Long.parseLong(RandomStringUtils.randomNumeric(6)), "title")),
                    ArticleView.class
            );

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
            assertThat(response.getBody().getTitle()).isEqualTo("title");
        }
    }

    private String buildPath(String path) {
        return "http://localhost:%s%s".formatted(serverPort, path);
    }
}