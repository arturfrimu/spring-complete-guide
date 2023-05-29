package com.artur.springjpa.entity;

import com.artur.springjpa.converter.PhoneNumberConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static java.util.Objects.requireNonNull;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Data
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
