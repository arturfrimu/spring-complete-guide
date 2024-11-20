package com.arturfrimu.elasticsearch.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "albums")
public class Album {
    @Id
    private Integer id;
    private Integer userId;
    private String title;
}

