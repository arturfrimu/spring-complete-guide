package com.arturfrimu.crud.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("developers")
public class DeveloperEntity implements Persistable<Long> {
    @Id
    Long id;
    String firstName;
    String lastName;
    String email;
    String speciality;
    Status status;

    @Override
    public boolean isNew() {
        return Objects.isNull(id);
    }

    public enum Status {
        ACTIVE, DELETED;
    }
}
