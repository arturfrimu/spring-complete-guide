package com.arturfrimu.crud.dto;

import com.arturfrimu.crud.entity.DeveloperEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * DTO for {@link com.arturfrimu.crud.entity.DeveloperEntity}
 */
@JsonInclude(NON_NULL)
public record DeveloperDto(
        Long id,
        String firstName,
        String lastName,
        String email,
        String speciality,
        String status
) implements Serializable {
    public static DeveloperDto fromEntity(DeveloperEntity entity) {
        return new DeveloperDto(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getSpeciality(),
                entity.getStatus().name()
        );
    }

    public DeveloperEntity toEntity() {
        return DeveloperEntity.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .speciality(speciality)
                .status(DeveloperEntity.Status.valueOf(status))
                .build();
    }
}