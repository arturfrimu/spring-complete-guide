package com.arturfrimu.elasticsearch.repository;

import com.arturfrimu.elasticsearch.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends ElasticsearchRepository<Todo, String> {

    @Query("""
            {
              "bool": {
                "should": [
                  {
                    "multi_match": {
                      "query": "?0",
                      "fields": ["title^3"],
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
                  }
                ],
                "filter": [
                  {
                    "term": {
                      "completed": "?1"
                    }
                  }
                ],
                "minimum_should_match": 1
              }
            }
            """)
    Page<Todo> searchByQuery(String searchText, Boolean completed, Pageable pageable);
}

