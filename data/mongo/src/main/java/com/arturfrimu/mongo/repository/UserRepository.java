package com.arturfrimu.mongo.repository;

import com.arturfrimu.mongo.document.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {

    List<User> findByCompanyName(String companyName);

    @Query("{ 'address.geo.lat': ?0, 'address.geo.lng': ?1 }")
    List<User> findByGeoLocation(String lat, String lng);

    @Query(value = "{ 'address.geo': { $nearSphere: { $geometry: { type: 'Point', coordinates: [ ?0, ?1 ] }, $maxDistance: ?2 } } }")
    List<User> findByProximity(double lng, double lat, double distance);
}
