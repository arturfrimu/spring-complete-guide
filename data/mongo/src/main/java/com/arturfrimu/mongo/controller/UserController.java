package com.arturfrimu.mongo.controller;

import com.arturfrimu.mongo.document.User;
import com.arturfrimu.mongo.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class UserController {

    UserRepository userRepository;
    RestTemplate restTemplate;

    @PostConstruct
    public void initUsers() {
        if (userRepository.count() == 0) {
            User[] users = restTemplate.getForObject("https://jsonplaceholder.typicode.com/users", User[].class);

            if (users != null) {
                userRepository.saveAll(Arrays.asList(users));
            }
        }
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: %s".formatted(id)));
    }

    @GetMapping("/company/{name}")
    public List<User> getUsersByCompanyName(@PathVariable String name) {
        return userRepository.findByCompanyName(name);
    }

    @GetMapping("/geo/{lat}/{lng}")
    public List<User> findByGeoLocation(@PathVariable String lat, @PathVariable String lng) {
        return userRepository.findByGeoLocation(lat, lng);
    }

    @GetMapping("/geo/{lat}/{lng}/{distance}/proximity")
    public List<User> findByProximity(@PathVariable final double lat, @PathVariable final double lng, @PathVariable final double distance) {
        return userRepository.findByProximity(lng, lat, distance);
    }
}
