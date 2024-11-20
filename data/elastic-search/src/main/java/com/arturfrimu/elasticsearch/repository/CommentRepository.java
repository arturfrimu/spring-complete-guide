package com.arturfrimu.elasticsearch.repository;

import com.arturfrimu.elasticsearch.entity.Comment;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends ElasticsearchRepository<Comment, String> {
}
