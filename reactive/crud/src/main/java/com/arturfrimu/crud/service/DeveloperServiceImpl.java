package com.arturfrimu.crud.service;

import com.arturfrimu.crud.entity.DeveloperEntity;
import com.arturfrimu.crud.exception.DeveloperNotFoundException;
import com.arturfrimu.crud.exception.DeveloperWithEmailAlreadyExistsException;
import com.arturfrimu.crud.repository.DeveloperRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static com.arturfrimu.crud.entity.DeveloperEntity.Status.ACTIVE;
import static com.arturfrimu.crud.entity.DeveloperEntity.Status.DELETED;
import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class DeveloperServiceImpl implements DeveloperService {

    DeveloperRepository developerRepository;

    Mono<Void> checkIfExistByEmail(String email) {
        return developerRepository.findByEmail(email)
                .flatMap(developer -> {
                    if (Objects.nonNull(developer)) {
                        return Mono.error(new DeveloperWithEmailAlreadyExistsException("Developer with defined email already exists", "DEVELOPER_WITH_DUPLICATE_EMAIL"));
                    }
                    return Mono.empty();
                });
    }

    @Override
    public Mono<DeveloperEntity> createDeveloper(DeveloperEntity developer) {
        return checkIfExistByEmail(developer.getEmail())
                .then(Mono.defer(() -> {
                    developer.setStatus(ACTIVE);
                    return developerRepository.save(developer);
                }));
    }

    @Override
    public Mono<DeveloperEntity> updateDeveloper(@NonNull DeveloperEntity developer) {
        return developerRepository.findById(developer.getId())
                .switchIfEmpty(Mono.error(new DeveloperNotFoundException("Developer not found", "DEVELOPER_NOT_FOUND")))
                .flatMap(existingDeveloper -> developerRepository.save(developer));
    }

    @Override
    public Flux<DeveloperEntity> findAll() {
        return developerRepository.findAll();
    }

    @Override
    public Flux<DeveloperEntity> findAllActiveBySpecialty(String speciality) {
        return developerRepository.findAllBySpeciality(speciality);
    }

    @Override
    public Mono<DeveloperEntity> findById(Long id) {
        return developerRepository.findById(id);
    }

    @Override
    public Mono<Void> softDeleteById(Long id) {
        return developerRepository.findById(id)
                .switchIfEmpty(Mono.error(new DeveloperNotFoundException("Developer not found", "DEVELOPER_NOT_FOUND")))
                .flatMap(existingDeveloper -> {
                    existingDeveloper.setStatus(DELETED);
                    return developerRepository.save(existingDeveloper).then();
                });
    }

    @Override
    public Mono<Void> hardDeleteById(Long id) {
        return developerRepository.findById(id)
                .switchIfEmpty(Mono.error(new DeveloperNotFoundException("Developer not found", "DEVELOPER_NOT_FOUND")))
                .flatMap(existingDeveloper -> developerRepository.deleteById(id).then());
    }
}
