package com.arturfrimu.elasticsearch.loader;

import com.arturfrimu.elasticsearch.entity.Todo;
import com.arturfrimu.elasticsearch.repository.TodoRepository;
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
public class TodoLoader implements CommandLineRunner {

    private static final String TODOS_URL_JSON_PLACEHOLDER = "https://jsonplaceholder.typicode.com/todos";

    TodoRepository todoRepository;
    RestTemplate restTemplate = new RestTemplate();

    public void fetchAndStoreTodos() {
        ResponseEntity<List<Todo>> response = restTemplate.exchange(RequestEntity.get(TODOS_URL_JSON_PLACEHOLDER).build(), todosList);
        todoRepository.saveAll(Objects.requireNonNull(response.getBody()));
        log.info("Saved {} todos", todoRepository.count());
    }

    @Override
    public void run(String... args) {
        todoRepository.deleteAll();

        fetchAndStoreTodos();
    }

    //@formatter:off
    private static final ParameterizedTypeReference<List<Todo>> todosList = new ParameterizedTypeReference<>() {};
}
