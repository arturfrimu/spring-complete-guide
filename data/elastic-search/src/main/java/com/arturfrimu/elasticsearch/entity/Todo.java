package com.arturfrimu.elasticsearch.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "todos")
public class Todo {
    @Id
    private Integer id;
    private Integer userId;
    private String title;
    private Boolean completed;
}

