package com.arturfrimu.elasticsearch.loader;

import com.arturfrimu.elasticsearch.entity.Album;
import com.arturfrimu.elasticsearch.repository.AlbumRepository;
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
public class AlbumLoader implements CommandLineRunner {

    private static final String ALBUMS_URL_JSON_PLACEHOLDER = "https://jsonplaceholder.typicode.com/albums";

    AlbumRepository albumRepository;
    RestTemplate restTemplate = new RestTemplate();

    public void fetchAndStoreAlbums() {
        ResponseEntity<List<Album>> response = restTemplate.exchange(RequestEntity.get(ALBUMS_URL_JSON_PLACEHOLDER).build(), albumsList);
        albumRepository.saveAll(Objects.requireNonNull(response.getBody()));
        log.info("Saved {} albums", albumRepository.count());
    }

    @Override
    public void run(String... args) {
        albumRepository.deleteAll();

        fetchAndStoreAlbums();
    }

    //@formatter:off
    private static final ParameterizedTypeReference<List<Album>> albumsList = new ParameterizedTypeReference<>() {};
}
