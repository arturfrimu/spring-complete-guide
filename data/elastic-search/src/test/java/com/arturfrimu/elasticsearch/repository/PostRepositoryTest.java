package com.arturfrimu.elasticsearch.repository;

import com.arturfrimu.elasticsearch.entity.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Test
    void contextLoads() {
        for (Post post : postRepository.findAll()) {
            System.out.println(post);
        }
    }
}