package com.arturfrimu.spring.complete.guide.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class UserPersonalInformation {
    private String username;
    private String password;
}
