package com.arturfrimu.elasticsearch.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "comments")
public class Comment {
    @Id
    private String id;
    private Integer postId;
    private String name;
    private String email;
    private String body;
}
