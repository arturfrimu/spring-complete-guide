package com.arturfrimu.mongo.document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Geo {
    private String type;
    private String[] coordinates;
}
