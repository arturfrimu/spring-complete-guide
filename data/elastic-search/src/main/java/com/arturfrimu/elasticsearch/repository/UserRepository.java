package com.arturfrimu.elasticsearch.repository;

import com.arturfrimu.elasticsearch.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends ElasticsearchRepository<User, String> {

    @Query("""
            {
              "bool": {
                "should": [
                  {
                    "multi_match": {
                      "query": "?0",
                      "fields": ["name^3", "username^2", "email^4", "phone", "website"],
                      "type": "best_fields",
                      "operator": "or"
                    }
                  },
                  {
                    "match_phrase": {
                      "name": {
                        "query": "?0",
                        "boost": 3
                      }
                    }
                  },
                  {
                    "match_phrase": {
                      "email": {
                        "query": "?0",
                        "boost": 4
                      }
                    }
                  },
                  {
                    "match_phrase": {
                      "phone": {
                        "query": "?0"
                      }
                    }
                  },
                  {
                    "match_phrase": {
                      "website": {
                        "query": "?0"
                      }
                    }
                  }
                ],
                "minimum_should_match": 1
              }
            }
            """)
    Page<User> searchByQuery(String searchText, Pageable pageable);
}
