package com.arturfrimu.crud.service;

import com.arturfrimu.crud.entity.DeveloperEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DeveloperService {

    Mono<DeveloperEntity> createDeveloper(DeveloperEntity developer);

    Mono<DeveloperEntity> updateDeveloper(DeveloperEntity developer);

    Flux<DeveloperEntity> findAll();

    Flux<DeveloperEntity> findAllActiveBySpecialty(String speciality);

    Mono<DeveloperEntity> findById(Long id);

    Mono<Void> softDeleteById(Long id);

    Mono<Void> hardDeleteById(Long id);
}
