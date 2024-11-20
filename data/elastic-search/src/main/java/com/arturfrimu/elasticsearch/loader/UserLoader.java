package com.arturfrimu.elasticsearch.loader;

import com.arturfrimu.elasticsearch.entity.User;
import com.arturfrimu.elasticsearch.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class UserLoader implements CommandLineRunner {

    private static final String USERS_URL_JSON_PLACEHOLDER = "https://jsonplaceholder.typicode.com/users";

    UserRepository userRepository;
    RestTemplate restTemplate = new RestTemplate();

    public void fetchAndStoreUsers() {
        ResponseEntity<List<User>> response = restTemplate.exchange(RequestEntity.get(USERS_URL_JSON_PLACEHOLDER).build(), usersList);
        userRepository.saveAll(Objects.requireNonNull(response.getBody()));
        log.info("Saved {} users", userRepository.count());
    }

    @Override
    public void run(String... args) {
        userRepository.deleteAll();

        fetchAndStoreUsers();
    }

    //@formatter:off
    private static final ParameterizedTypeReference<List<User>> usersList = new ParameterizedTypeReference<>() {};
}
