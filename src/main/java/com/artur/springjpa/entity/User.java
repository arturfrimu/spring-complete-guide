package com.artur.springjpa.entity;

import com.artur.springjpa.converter.PhoneNumberConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="USERS")
@NoArgsConstructor
@Data
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String username;
    private String password;
    @Convert(converter = PhoneNumberConverter.class)
    private String phoneNumber;
}
