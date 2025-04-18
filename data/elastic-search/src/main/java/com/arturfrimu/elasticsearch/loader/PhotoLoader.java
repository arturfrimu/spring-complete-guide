package com.arturfrimu.elasticsearch.loader;

import com.arturfrimu.elasticsearch.entity.Photo;
import com.arturfrimu.elasticsearch.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
public class PhotoLoader implements CommandLineRunner {

    @NonFinal
    @Value("${script.populate-elastic-search}")
    private Boolean populateElasticSearch;

    private static final String PHOTOS_URL_JSON_PLACEHOLDER = "https://jsonplaceholder.typicode.com/photos";

    PhotoRepository photoRepository;
    RestTemplate restTemplate = new RestTemplate();

    public void fetchAndStorePhotos() {
        ResponseEntity<List<Photo>> response = restTemplate.exchange(RequestEntity.get(PHOTOS_URL_JSON_PLACEHOLDER).build(), photosList);
        photoRepository.saveAll(Objects.requireNonNull(response.getBody()));
        log.info("Saved {} photos", photoRepository.count());
    }

    @Override
    public void run(String... args) {
        if (populateElasticSearch) {
            photoRepository.deleteAll();

            fetchAndStorePhotos();
        }
    }

    //@formatter:off
    private static final ParameterizedTypeReference<List<Photo>> photosList = new ParameterizedTypeReference<>() {};
}
