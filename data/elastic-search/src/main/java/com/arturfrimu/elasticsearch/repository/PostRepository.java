package com.arturfrimu.elasticsearch.repository;

import com.arturfrimu.elasticsearch.entity.Post;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends ElasticsearchRepository<Post, String> {
}
