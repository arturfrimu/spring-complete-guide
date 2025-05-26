package com.arturfrimu.nomenclatures.third;


import com.arturfrimu.nomenclatures.third.dto.ArticleView;
import com.arturfrimu.nomenclatures.third.dto.UserView;
import com.arturfrimu.nomenclatures.third.service.CrudNomenclatureService;
import com.arturfrimu.nomenclatures.third.types.NomenclatureType;
import com.arturfrimu.nomenclatures.third.types.PageResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.LinkedHashMap;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NomControllerTest {

    private static final String CREATE = "/nomenclature";
    private static final String UPDATE = "/nomenclature";
    private static final String FIND_BY_ID = "/nomenclature/%s";
    private static final String FIND_PAGE = "/nomenclature";
    private static final String DELETE = "/nomenclature/%s";
    private static final String DELETE_ALL = "/nomenclature";

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private CrudNomenclatureService crudNomenclatureService;

    @BeforeEach
    public void cleanup() {
        crudNomenclatureService.deleteAll(NomenclatureType.USERS);
        crudNomenclatureService.deleteAll(NomenclatureType.ARTICLES);
    }

    @LocalServerPort
    private int serverPort;

    @Nested
    class Create {
        @Test
        void createUser() {
            ResponseEntity<UserView> response = testRestTemplate.exchange(
                    RequestEntity.post(URI.create(buildPath(CREATE)))
                            .body(new UserView(null, "John", "Doe")),
                    new ParameterizedTypeReference<>() {}
            );

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
            assertThat(response.getBody()).isNotNull();
            assertThat(response.getBody().getName()).isEqualTo("John");
            assertThat(response.getBody().getSurname()).isEqualTo("Doe");
        }

        @Test
        void createArticle() {
            ResponseEntity<ArticleView> response = testRestTemplate.exchange(
                    RequestEntity.post(URI.create(buildPath(CREATE)))
                            .body(new ArticleView(null, "title")),
                    new ParameterizedTypeReference<>() {}
            );

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
            assertThat(response.getBody()).isNotNull();
            assertThat(response.getBody().getTitle()).isEqualTo("title");
        }
    }

    @Nested
    class Update {
        @Test
        void updateUser() {
            ResponseEntity<UserView> createdUser = testRestTemplate.exchange(
                    RequestEntity.post(URI.create(buildPath(CREATE)))
                            .body(new UserView(null, "John", "Doe")),
                    new ParameterizedTypeReference<>() {}
            );

            ResponseEntity<UserView> updatedUser = testRestTemplate.exchange(
                    RequestEntity.put(URI.create(buildPath(UPDATE)))
                            .body(new UserView(createdUser.getBody().getId(), "Andrew", "Black")),
                    new ParameterizedTypeReference<>() {}
            );

            assertThat(updatedUser.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(updatedUser.getBody()).isNotNull();
            assertThat(updatedUser.getBody().getName()).isEqualTo("Andrew");
            assertThat(updatedUser.getBody().getSurname()).isEqualTo("Black");
        }

        @Test
        void updateArticle() {
            ResponseEntity<ArticleView> createdArticle = testRestTemplate.exchange(
                    RequestEntity.post(URI.create(buildPath(CREATE)))
                            .body(new ArticleView(Long.parseLong(RandomStringUtils.randomNumeric(6)), "title")),
                    new ParameterizedTypeReference<>() {
                    }
            );

            ResponseEntity<ArticleView> updatedArticle = testRestTemplate.exchange(
                    RequestEntity.put(URI.create(buildPath(UPDATE)))
                            .body(new ArticleView(createdArticle.getBody().getId(), "newTitle")),
                    new ParameterizedTypeReference<>() {
                    }
            );

            assertThat(updatedArticle.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(updatedArticle.getBody()).isNotNull();
            assertThat(updatedArticle.getBody().getTitle()).isEqualTo("newTitle");
        }
    }

    @Nested
    class FindById {
        @Test
        void findUser() {
            ResponseEntity<UserView> createdUser = testRestTemplate.exchange(
                    RequestEntity.post(URI.create(buildPath(CREATE)))
                            .body(new UserView(Long.parseLong(RandomStringUtils.randomNumeric(6)), "John", "Doe")),
                    new ParameterizedTypeReference<>() {
                    }
            );

            ResponseEntity<UserView> user = testRestTemplate.exchange(
                    RequestEntity.get(UriComponentsBuilder
                                    .fromUriString(buildPath(FIND_BY_ID.formatted(createdUser.getBody().getId())))
                                    .queryParam("type", "USERS").build().toUri())
                            .build(),
                    new ParameterizedTypeReference<>() {
                    }
            );

            assertThat(user.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(user.getBody()).isNotNull();
            assertThat(user.getBody().getName()).isEqualTo("John");
            assertThat(user.getBody().getSurname()).isEqualTo("Doe");
        }

        @Test
        void findArticle() {
            ResponseEntity<ArticleView> createdArticle = testRestTemplate.exchange(
                    RequestEntity.post(URI.create(buildPath(CREATE)))
                            .body(new ArticleView(Long.parseLong(RandomStringUtils.randomNumeric(6)), "title")),
                    new ParameterizedTypeReference<>() {
                    }
            );

            ResponseEntity<ArticleView> article = testRestTemplate.exchange(
                    RequestEntity.get(UriComponentsBuilder
                            .fromUriString(buildPath(FIND_BY_ID.formatted(createdArticle.getBody().getId())))
                            .queryParam("type", "ARTICLES").build().toUri()).build(),
                    new ParameterizedTypeReference<>() {
                    }
            );

            assertThat(article.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(article.getBody()).isNotNull();
            assertThat(article.getBody().getTitle()).isEqualTo("title");
        }
    }

    @Nested
    class FindPage {
        @Test
        void findUserPage() {
            testRestTemplate.exchange(
                    RequestEntity.post(URI.create(buildPath(CREATE)))
                            .body(new UserView(Long.parseLong(RandomStringUtils.randomNumeric(6)), "John", "Doe")),
                    new ParameterizedTypeReference<>() {
                    }
            );

            testRestTemplate.exchange(
                    RequestEntity.post(URI.create(buildPath(CREATE)))
                            .body(new UserView(Long.parseLong(RandomStringUtils.randomNumeric(6)), "Andrew", "Black")),
                    new ParameterizedTypeReference<>() {
                    }
            );

            ResponseEntity<PageResponse<UserView>> john = testRestTemplate.exchange(
                    RequestEntity.get(UriComponentsBuilder
                                    .fromUriString(buildPath(FIND_PAGE))
                                    .queryParam("type", "USERS")
                                    .queryParam("size", "1")
                                    .queryParam("page", "0")
                                    .build().toUri())
                            .build(),
                    new ParameterizedTypeReference<>() {
                    }
            );

            assertThat(john.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(john.getBody()).isNotNull();
            assertThat(john.getBody().content()).isNotNull().isNotEmpty();
            assertThat(john.getBody().totalElements()).isEqualTo(2);
            assertThat(john.getBody().content().getFirst().getId()).isNotNull().isNotNegative();
            assertThat(john.getBody().content().getFirst().getName()).isEqualTo("John");
            assertThat(john.getBody().content().getFirst().getSurname()).isEqualTo("Doe");

            ResponseEntity<PageResponse<UserView>> andrew = testRestTemplate.exchange(
                    RequestEntity.get(UriComponentsBuilder
                                    .fromUriString(buildPath(FIND_PAGE))
                                    .queryParam("type", "USERS")
                                    .queryParam("size", "1")
                                    .queryParam("page", "1")
                                    .build().toUri())
                            .build(),
                    new ParameterizedTypeReference<>() {
                    }
            );

            assertThat(andrew.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(andrew.getBody()).isNotNull();
            assertThat(andrew.getBody().content()).isNotNull().isNotEmpty();
            assertThat(andrew.getBody().totalElements()).isEqualTo(2);
            assertThat(andrew.getBody().content().getFirst().getId()).isNotNull().isNotNegative();
            assertThat(andrew.getBody().content().getFirst().getName()).isEqualTo("Andrew");
            assertThat(andrew.getBody().content().getFirst().getSurname()).isEqualTo("Black");
        }

        @Test
        void findUserPageSortedByNameDescAndSurnameAsc() {
            testRestTemplate.exchange(
                    RequestEntity.post(URI.create(buildPath(CREATE)))
                            .body(new UserView(Long.parseLong(RandomStringUtils.randomNumeric(6)), "Ball", "Fran")),
                    new ParameterizedTypeReference<>() {}
            );

            testRestTemplate.exchange(
                    RequestEntity.post(URI.create(buildPath(CREATE)))
                            .body(new UserView(Long.parseLong(RandomStringUtils.randomNumeric(6)), "Albert", "Colt")),
                    new ParameterizedTypeReference<>() {}
            );

            testRestTemplate.exchange(
                    RequestEntity.post(URI.create(buildPath(CREATE)))
                            .body(new UserView(Long.parseLong(RandomStringUtils.randomNumeric(6)), "Albert", "Pick")),
                    new ParameterizedTypeReference<>() {}
            );

            ResponseEntity<PageResponse<UserView>> page = testRestTemplate.exchange(
                    RequestEntity.get(UriComponentsBuilder
                                    .fromUriString(buildPath(FIND_PAGE))
                                    .queryParam("type", "USERS")
                                    .queryParam("size", "3")
                                    .queryParam("page", "0")
                                    .queryParam("sort", "name,asc")
                                    .queryParam("sort", "surname,desc")
                                    .build().toUri())
                            .build(),
                    new ParameterizedTypeReference<>() {}
            );

            assertThat(page.getBody().content().get(0).getName()).isEqualTo("Albert");
            assertThat(page.getBody().content().get(0).getSurname()).isEqualTo("Pick");

            assertThat(page.getBody().content().get(1).getName()).isEqualTo("Albert");
            assertThat(page.getBody().content().get(1).getSurname()).isEqualTo("Colt");

            assertThat(page.getBody().content().get(2).getName()).isEqualTo("Ball");
            assertThat(page.getBody().content().get(2).getSurname()).isEqualTo("Fran");
        }

        @Test
        void findArticlePage() {
            testRestTemplate.exchange(
                    RequestEntity.post(URI.create(buildPath(CREATE)))
                            .body(new ArticleView(Long.parseLong(RandomStringUtils.randomNumeric(6)), "Title 1")),
                    new ParameterizedTypeReference<>() {
                    }
            );

            testRestTemplate.exchange(
                    RequestEntity.post(URI.create(buildPath(CREATE)))
                            .body(new ArticleView(Long.parseLong(RandomStringUtils.randomNumeric(6)), "Title 2")),
                    new ParameterizedTypeReference<>() {
                    }
            );

            ResponseEntity<PageResponse<ArticleView>> title1 = testRestTemplate.exchange(
                    RequestEntity.get(UriComponentsBuilder
                                    .fromUriString(buildPath(FIND_PAGE))
                                    .queryParam("type", "ARTICLES")
                                    .queryParam("size", "1")
                                    .queryParam("page", "0")
                                    .build().toUri())
                            .build(),
                    new ParameterizedTypeReference<>() {
                    }
            );

            assertThat(title1.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(title1.getBody()).isNotNull();
            assertThat(title1.getBody().content()).isNotNull().isNotEmpty();
            assertThat(title1.getBody().totalElements()).isEqualTo(2);
            assertThat(title1.getBody().content().getFirst().getId()).isNotNull().isNotNegative();
            assertThat(title1.getBody().content().getFirst().getTitle()).isEqualTo("Title 1");

            ResponseEntity<PageResponse<ArticleView>> title2 = testRestTemplate.exchange(
                    RequestEntity.get(UriComponentsBuilder
                                    .fromUriString(buildPath(FIND_PAGE))
                                    .queryParam("type", "ARTICLES")
                                    .queryParam("size", "1")
                                    .queryParam("page", "1")
                                    .build().toUri())
                            .build(),
                    new ParameterizedTypeReference<>() {
                    }
            );

            assertThat(title2.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(title2.getBody()).isNotNull();
            assertThat(title2.getBody().content()).isNotNull().isNotEmpty();
            assertThat(title2.getBody().totalElements()).isEqualTo(2);
            assertThat(title2.getBody().content().getFirst().getId()).isNotNull().isNotNegative();
            assertThat(title2.getBody().content().getFirst().getTitle()).isEqualTo("Title 2");
        }

        @Test
        void findArticlePageSortedByTitleDesc() {
            testRestTemplate.exchange(
                    RequestEntity.post(URI.create(buildPath(CREATE)))
                            .body(new ArticleView(Long.parseLong(RandomStringUtils.randomNumeric(6)), "A")),
                    new ParameterizedTypeReference<>() {
                    }
            );

            testRestTemplate.exchange(
                    RequestEntity.post(URI.create(buildPath(CREATE)))
                            .body(new ArticleView(Long.parseLong(RandomStringUtils.randomNumeric(6)), "B")),
                    new ParameterizedTypeReference<>() {
                    }
            );

            ResponseEntity<PageResponse<ArticleView>> articlePage = testRestTemplate.exchange(
                    RequestEntity.get(UriComponentsBuilder
                                    .fromUriString(buildPath(FIND_PAGE))
                                    .queryParam("type", "ARTICLES")
                                    .queryParam("size", "2")
                                    .queryParam("page", "0")
                                    .queryParam("sort", "title,desc")
                                    .build().toUri())
                            .build(),
                    new ParameterizedTypeReference<>() {
                    }
            );

            assertThat(articlePage.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(articlePage.getBody()).isNotNull();
            assertThat(articlePage.getBody().content()).isNotNull().isNotEmpty();
            assertThat(articlePage.getBody().totalElements()).isEqualTo(2);
            assertThat(articlePage.getBody().content().getFirst().getId()).isNotNull().isNotNegative();

            assertThat(articlePage.getBody().content().getFirst().getTitle()).isEqualTo("B");
            assertThat(articlePage.getBody().content().getLast().getTitle()).isEqualTo("A");
        }
    }

    @Nested
    class Delete {
        @Test
        void deleteUser() {
            ResponseEntity<UserView> createdUser = testRestTemplate.exchange(
                    RequestEntity.post(URI.create(buildPath(CREATE)))
                            .body(new UserView(Long.parseLong(RandomStringUtils.randomNumeric(6)), "John", "Doe")),
                    new ParameterizedTypeReference<>() {
                    }
            );

            ResponseEntity<String> deleted = testRestTemplate.exchange(
                    RequestEntity.delete(UriComponentsBuilder
                                    .fromUriString(buildPath(DELETE.formatted(createdUser.getBody().getId())))
                                    .queryParam("type", "USERS")
                                    .build().toUri())
                            .build(),
                    new ParameterizedTypeReference<>() {
                    }
            );

            assertThat(deleted.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(deleted.getBody()).isNull();

            ResponseEntity<LinkedHashMap<String, String>> error = testRestTemplate.exchange(
                    RequestEntity.get(UriComponentsBuilder
                            .fromUriString(buildPath(FIND_BY_ID.formatted(createdUser.getBody().getId())))
                            .queryParam("type", "USERS").build().toUri()).build(),
                    new ParameterizedTypeReference<>() {
                    }
            );

            assertThat(error.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
            assertThat(error.getBody().get("error")).isEqualTo("Internal Server Error");
            assertThat(error.getBody().get("status")).isEqualTo("500");
        }

        @Test
        void deleteArticle() {
            ResponseEntity<ArticleView> createdArticle = testRestTemplate.exchange(
                    RequestEntity.post(URI.create(buildPath(CREATE)))
                            .body(new ArticleView(Long.parseLong(RandomStringUtils.randomNumeric(6)), "Title 1")),
                    new ParameterizedTypeReference<>() {
                    }
            );

            ResponseEntity<String> deleted = testRestTemplate.exchange(
                    RequestEntity.delete(UriComponentsBuilder
                                    .fromUriString(buildPath(DELETE.formatted(createdArticle.getBody().getId())))
                                    .queryParam("type", "ARTICLES")
                                    .build().toUri())
                            .build(),
                    new ParameterizedTypeReference<>() {
                    }
            );

            assertThat(deleted.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(deleted.getBody()).isNull();

            ResponseEntity<LinkedHashMap<String, String>> error = testRestTemplate.exchange(
                    RequestEntity.get(UriComponentsBuilder
                            .fromUriString(buildPath(FIND_BY_ID.formatted(createdArticle.getBody().getId())))
                            .queryParam("type", "ARTICLES").build().toUri()).build(),
                    new ParameterizedTypeReference<>() {
                    }
            );

            assertThat(error.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
            assertThat(error.getBody().get("error")).isEqualTo("Internal Server Error");
            assertThat(error.getBody().get("status")).isEqualTo("500");
        }
    }

    @Nested
    class DeleteAll {
        @Test
        void deleteAllUsers() {
            testRestTemplate.exchange(
                    RequestEntity.post(URI.create(buildPath(CREATE)))
                            .body(new UserView(Long.parseLong(RandomStringUtils.randomNumeric(6)), "John", "Doe")),
                    new ParameterizedTypeReference<>() {
                    }
            );

            testRestTemplate.exchange(
                    RequestEntity.post(URI.create(buildPath(CREATE)))
                            .body(new UserView(Long.parseLong(RandomStringUtils.randomNumeric(6)), "Andrew", "Black")),
                    new ParameterizedTypeReference<>() {
                    }
            );

            ResponseEntity<String> deleted = testRestTemplate.exchange(
                    RequestEntity.delete(UriComponentsBuilder
                                    .fromUriString(buildPath(DELETE_ALL))
                                    .queryParam("type", "USERS")
                                    .build().toUri())
                            .build(),
                    new ParameterizedTypeReference<>() {
                    }
            );

            assertThat(deleted.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(deleted.getBody()).isNull();

            ResponseEntity<PageResponse<UserView>> response = testRestTemplate.exchange(
                    RequestEntity.get(UriComponentsBuilder
                                    .fromUriString(buildPath(FIND_PAGE))
                                    .queryParam("type", "USERS")
                                    .queryParam("size", "1")
                                    .queryParam("page", "0")
                                    .build().toUri())
                            .build(),
                    new ParameterizedTypeReference<>() {
                    }
            );

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isNotNull();
            assertThat(response.getBody().content()).isEmpty();
            assertThat(response.getBody().totalElements()).isEqualTo(0);
        }

        @Test
        void deleteAllArticles() {
            testRestTemplate.exchange(
                    RequestEntity.post(URI.create(buildPath(CREATE)))
                            .body(new ArticleView(Long.parseLong(RandomStringUtils.randomNumeric(6)), "Title 1")),
                    new ParameterizedTypeReference<>() {
                    }
            );

            testRestTemplate.exchange(
                    RequestEntity.post(URI.create(buildPath(CREATE)))
                            .body(new ArticleView(Long.parseLong(RandomStringUtils.randomNumeric(6)), "Title 2")),
                    new ParameterizedTypeReference<>() {
                    }
            );

            ResponseEntity<String> deleted = testRestTemplate.exchange(
                    RequestEntity.delete(UriComponentsBuilder
                                    .fromUriString(buildPath(DELETE_ALL))
                                    .queryParam("type", "ARTICLES")
                                    .build().toUri())
                            .build(),
                    new ParameterizedTypeReference<>() {
                    }
            );

            assertThat(deleted.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(deleted.getBody()).isNull();

            ResponseEntity<PageResponse<ArticleView>> response = testRestTemplate.exchange(
                    RequestEntity.get(UriComponentsBuilder
                                    .fromUriString(buildPath(FIND_PAGE))
                                    .queryParam("type", "ARTICLES")
                                    .queryParam("size", "1")
                                    .queryParam("page", "0")
                                    .build().toUri())
                            .build(),
                    new ParameterizedTypeReference<>() {
                    }
            );

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isNotNull();
            assertThat(response.getBody().content()).isEmpty();
            assertThat(response.getBody().totalElements()).isEqualTo(0);
        }
    }

    private String buildPath(String path) {
        return "http://localhost:%s%s".formatted(serverPort, path);
    }
}