package com.arturfrimu.elasticsearch.repository;

import com.arturfrimu.elasticsearch.entity.Album;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends ElasticsearchRepository<Album, String> {
    @Query("""
        {
          "bool": {
            "should": [
              {
                "multi_match": {
                  "query": "?0",
                  "fields": ["title^4"],
                  "type": "best_fields",
                  "operator": "or"
                }
              },
              {
                "match_phrase": {
                  "title": {
                    "query": "?0",
                    "boost": 3
                  }
                }
              }
            ],
            "minimum_should_match": 1
          }
        }
    """)
    Page<Album> searchByQuery(String searchText, Pageable pageable);
}
