package com.arturfrimu.crud.controller;

import com.arturfrimu.crud.dto.DeveloperDto;
import com.arturfrimu.crud.service.DeveloperService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/developers")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DeveloperRestControllerV1 {

    DeveloperService developerService;

    @PostMapping
    public Mono<DeveloperDto> createDeveloper(@RequestBody DeveloperDto dto) {
        return developerService.createDeveloper(dto.toEntity())
                .flatMap(entity -> Mono.just(DeveloperDto.fromEntity(entity)));
    }

    @PutMapping
    public Mono<DeveloperDto> updateDeveloper(@RequestBody DeveloperDto dto) {
        return developerService.updateDeveloper(dto.toEntity())
                .flatMap(entity -> Mono.just(DeveloperDto.fromEntity(entity)));
    }

    @GetMapping
    public Flux<DeveloperDto> findAll() {
        return developerService.findAll()
                .flatMap(entity -> {
                    DeveloperDto data = DeveloperDto.fromEntity(entity);
                    log.info("" + data);
                    return Mono.just(data);
                });
    }

    @GetMapping("/speciality/{speciality}")
    public Flux<DeveloperDto> findAllActiveBySpecialty(@PathVariable String speciality) {
        return developerService.findAllActiveBySpecialty(speciality)
                .flatMap(entity -> Mono.just(DeveloperDto.fromEntity(entity)));
    }

    @GetMapping("/{id}")
    public Mono<DeveloperDto> findById(@PathVariable Long id) {
        return developerService.findById(id)
                .flatMap(entity -> Mono.just(DeveloperDto.fromEntity(entity)));
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteById(@PathVariable Long id, @RequestParam(value = "isHard", required = false, defaultValue = "false") boolean isHard) {
        if (isHard) {
            return developerService.hardDeleteById(id);
        }
        return developerService.softDeleteById(id);
    }
}
