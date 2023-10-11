package com.arturfrimu.spring.complete.guide.entity;

import com.arturfrimu.spring.complete.guide.converter.PhoneNumberConverter;
import lombok.*;

import javax.persistence.*;

import static java.util.Objects.requireNonNull;
import static javax.persistence.GenerationType.IDENTITY;

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
