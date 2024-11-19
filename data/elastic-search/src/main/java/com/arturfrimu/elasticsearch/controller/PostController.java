package com.arturfrimu.elasticsearch.controller;


import com.arturfrimu.elasticsearch.entity.Post;
import com.arturfrimu.elasticsearch.repository.PostRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostController {

    PostRepository postRepository;

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        return ResponseEntity.ok(postRepository.save(post));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> findById(@PathVariable String id) {
        return ResponseEntity.ok(postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id %s".formatted(id))));
    }

    @GetMapping
    public ResponseEntity<Iterable<Post>> findAll() {
        return ResponseEntity.ok(postRepository.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable String id) {
        postRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public Page<Post> searchPosts(
            @RequestParam String searchText,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "100") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return postRepository.searchByQuery(searchText, pageable);
    }
}
