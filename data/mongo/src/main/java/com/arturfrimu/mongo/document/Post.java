package com.arturfrimu.mongo.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@RequiredArgsConstructor
@Document(collection = "post")
public class Post {
    @Id
    private String id;
    private Integer userId;
    private String title;
    private String body;
}
