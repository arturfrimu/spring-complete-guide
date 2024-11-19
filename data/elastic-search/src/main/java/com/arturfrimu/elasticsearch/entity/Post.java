package com.arturfrimu.elasticsearch.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "posts")
public class Post {
    @Id
    private String id;
    private Long userId;
    private String title;
    private String body;
}
