package com.arturfrimu.elasticsearch.loader;

import com.arturfrimu.elasticsearch.entity.Post;
import com.arturfrimu.elasticsearch.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class PostLoader implements CommandLineRunner {

    @NonFinal
    @Value("${script.populate-elastic-search}")
    private Boolean populateElasticSearch;

    private static final String POSTS_URL_JSON_PLACEHOLDER = "https://jsonplaceholder.typicode.com/posts";

    PostRepository postRepository;
    RestTemplate restTemplate = new RestTemplate();

    public void fetchAndStorePosts() {
        ResponseEntity<List<Post>> response = restTemplate.exchange(RequestEntity.get(POSTS_URL_JSON_PLACEHOLDER).build(), postsList);
        postRepository.saveAll(Objects.requireNonNull(response.getBody()));
        log.info("Saved {} posts", postRepository.count());
    }

    @Override
    public void run(String... args) {
        if (populateElasticSearch) {
            postRepository.deleteAll();

            fetchAndStorePosts();
        }
    }

    //@formatter:off
    private static final ParameterizedTypeReference<List<Post>> postsList = new ParameterizedTypeReference<>() {};
}
