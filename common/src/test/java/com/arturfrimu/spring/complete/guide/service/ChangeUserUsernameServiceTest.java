package com.arturfrimu.spring.complete.guide.service;

import annotation.ServiceTest;
import com.arturfrimu.common.entity.User;
import com.arturfrimu.common.entity.UserPersonalInformation;
import com.arturfrimu.common.repository.UserRepository;
import com.arturfrimu.common.service.transactional.propagation.ChangeUserUsernameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.assertj.core.api.Assertions.assertThat;

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