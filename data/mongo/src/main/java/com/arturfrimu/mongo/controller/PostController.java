package com.arturfrimu.mongo.controller;

import com.arturfrimu.mongo.document.Post;
import com.arturfrimu.mongo.repository.PostRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpMethod.GET;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class PostController {

    PostRepository postRepository;
    RestTemplate restTemplate;

    @PostConstruct
    void postConstruct() {
        if (postRepository.count() == 0) {
            ResponseEntity<List<Post>> posts = restTemplate.exchange("https://jsonplaceholder.typicode.com/posts", GET, null, new ParameterizedTypeReference<>() {
            });
            if (posts.getBody() != null) {
                postRepository.saveAll(posts.getBody());
            }
        }
    }

    @PostMapping
    public Post createPost(@RequestBody Post post) {
        return postRepository.save(post);
    }

    @GetMapping
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @GetMapping("/{id}")
    public Post getPost(@PathVariable String id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id: %s".formatted(id)));
    }
}
