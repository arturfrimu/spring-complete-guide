package com.arturfrimu.elasticsearch.repository;

import com.arturfrimu.elasticsearch.entity.Photo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepository extends ElasticsearchRepository<Photo, String> {
    @Query("""
    {
      "bool": {
        "should": [
          {
            "multi_match": {
              "query": "?0",
              "fields": ["title^3", "url^2", "thumbnailUrl"],
              "type": "best_fields",
              "operator": "or"
            }
          },
          {
            "match_phrase": {
              "title": {
                "query": "?0",
                "boost": 2
              }
            }
          },
          {
            "match_phrase": {
              "url": {
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

    Page<Photo> searchByQuery(String searchText, Pageable pageable);
}
