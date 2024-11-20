package com.arturfrimu.elasticsearch.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "photos")
public class Photo {
    @Id
    private Integer id;
    private Integer albumId;
    private String title;
    private String url;
    private String thumbnailUrl;
}
