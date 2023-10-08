package com.artur.springjpa.service;

import com.artur.springjpa.entity.User;
import com.artur.springjpa.entity.UserPersonalInformation;
import com.artur.springjpa.exception.ResourceNotFoundException;
import com.artur.springjpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChangeUserUsernameService implements ChangeUserUsernameUseCase {

    private final UserRepository userRepository;

    @Override
    public void change(Long userId, String username) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: %s".formatted(userId)));

        UserPersonalInformation personalInformation = user.getPersonalInformation();
        personalInformation.setUsername(username);
    }
}
