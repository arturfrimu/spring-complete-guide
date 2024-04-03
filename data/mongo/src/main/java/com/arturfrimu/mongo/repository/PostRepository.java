package com.arturfrimu.mongo.repository;

import com.arturfrimu.mongo.document.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepository extends MongoRepository<Post, String> {
}
