package com.arturfrimu.elasticsearch.repository;

import com.arturfrimu.elasticsearch.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends ElasticsearchRepository<Comment, String> {
    @Query("""
        {
          "bool": {
            "should": [
              {
                "multi_match": {
                  "query": "?0",
                  "fields": ["name^3", "body^2", "email"],
                  "type": "best_fields",
                  "operator": "or"
                }
              },
              {
                "match_phrase": {
                  "name": {
                    "query": "?0",
                    "boost": 2
                  }
                }
              },
              {
                "match_phrase": {
                  "body": {
                    "query": "?0",
                    "boost": 1
                  }
                }
              }
            ],
            "minimum_should_match": 1
          }
        }
    """)
    Page<Comment> searchByQuery(String searchText, Pageable pageable);
}
