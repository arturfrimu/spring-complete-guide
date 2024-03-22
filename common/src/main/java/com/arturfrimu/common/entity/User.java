package com.arturfrimu.common.entity;

import com.arturfrimu.common.converter.PhoneNumberConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.GenerationType.IDENTITY;
import static java.util.Objects.requireNonNull;

@Entity
@Table(name = "USERS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Convert(converter = PhoneNumberConverter.class)
    private String phoneNumber;
    @Embedded
    private UserPersonalInformation personalInformation;

    public User(String phoneNumber, UserPersonalInformation personalInformation) {
        this.personalInformation = requireNonNull(personalInformation);
        this.phoneNumber = requireNonNull(phoneNumber);
    }
}
