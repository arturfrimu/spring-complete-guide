package com.arturfrimu.elasticsearch.controller;


import com.arturfrimu.elasticsearch.entity.Post;
import com.arturfrimu.elasticsearch.repository.PostRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostRestController {

    PostRepository repository;

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        return ResponseEntity.ok(repository.save(post));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> findById(@PathVariable String id) {
        return ResponseEntity.ok(repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with id %s".formatted(id))));
    }

    @GetMapping
    public ResponseEntity<Iterable<Post>> findAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable String id) {
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
