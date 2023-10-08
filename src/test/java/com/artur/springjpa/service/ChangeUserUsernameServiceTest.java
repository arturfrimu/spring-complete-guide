package com.artur.springjpa.service;

import annotation.ServiceTest;
import com.artur.springjpa.entity.User;
import com.artur.springjpa.entity.UserPersonalInformation;
import com.artur.springjpa.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ServiceTest
class ChangeUserUsernameServiceTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ChangeUserUsernameService changeUserUsernameService;

    @Test
    void change() {
        userRepository.save(new User(1L, "12345", new UserPersonalInformation("oldUsername", "oldPassword")));
        changeUserUsernameService.change(1L, "newUsername");

        assertThat(userRepository.findById(1L).get().getPersonalInformation().getUsername())
                .isEqualTo("newUsername");
    }
}