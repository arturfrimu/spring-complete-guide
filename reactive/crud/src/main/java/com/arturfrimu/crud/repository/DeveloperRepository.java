package com.arturfrimu.crud.repository;

import com.arturfrimu.crud.entity.DeveloperEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DeveloperRepository extends R2dbcRepository<DeveloperEntity, Long> {

    Mono<DeveloperEntity> findByEmail(String email);

    @Query("SELECT d FROM DevelopersEntity d WHERE d.speciality = :speciality and d.status = 'ACTIVE'")
    Flux<DeveloperEntity> findAllBySpeciality(String speciality);
}
