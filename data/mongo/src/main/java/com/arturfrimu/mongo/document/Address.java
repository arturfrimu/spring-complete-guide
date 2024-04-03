package com.arturfrimu.mongo.document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;

import static org.springframework.data.mongodb.core.index.GeoSpatialIndexType.GEO_2DSPHERE;

@Getter
@Setter
@NoArgsConstructor
public class Address {
    private String street;
    private String suite;
    private String city;
    private String zipcode;
    @GeoSpatialIndexed(type = GEO_2DSPHERE)
    private Geo geo;
}
