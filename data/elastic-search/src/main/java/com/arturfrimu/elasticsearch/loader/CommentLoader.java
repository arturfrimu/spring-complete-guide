package com.arturfrimu.elasticsearch.loader;

import com.arturfrimu.elasticsearch.entity.Comment;
import com.arturfrimu.elasticsearch.repository.CommentRepository;
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
public class CommentLoader implements CommandLineRunner {

    @NonFinal
    @Value("${script.populate-elastic-search}")
    private Boolean populateElasticSearch;

    private static final String COMMENTS_URL_JSON_PLACEHOLDER = "https://jsonplaceholder.typicode.com/comments";

    CommentRepository commentRepository;
    RestTemplate restTemplate = new RestTemplate();

    public void fetchAndStoreComments() {
        ResponseEntity<List<Comment>> response = restTemplate.exchange(RequestEntity.get(COMMENTS_URL_JSON_PLACEHOLDER).build(), commentsList);
        commentRepository.saveAll(Objects.requireNonNull(response.getBody()));
        log.info("Saved {} comments", commentRepository.count());
    }

    @Override
    public void run(String... args) {
        if (populateElasticSearch) {
            commentRepository.deleteAll();

            fetchAndStoreComments();
        }
    }

    //@formatter:off
    private static final ParameterizedTypeReference<List<Comment>> commentsList = new ParameterizedTypeReference<>() {};
}
