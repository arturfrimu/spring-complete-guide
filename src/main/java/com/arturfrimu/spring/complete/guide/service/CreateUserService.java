package com.arturfrimu.spring.complete.guide.service;

import com.arturfrimu.spring.complete.guide.mapper.UserMapper;
import com.arturfrimu.spring.complete.guide.repository.UserRepository;
import com.arturfrimu.spring.complete.guide.service.command.CreateUserCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static java.lang.String.format;
import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class CreateUserService implements CreateUserUseCase {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public void create(CreateUserCommand command) {
        validate(command);
        var newUser = userMapper.toUser(command);
        userRepository.save(newUser);
    }

    private static void validate(CreateUserCommand command) {
        if (isNull(command.phoneNumber()) || command.phoneNumber().isBlank()) {
            throw new IllegalArgumentException(format("Phone number can not be blank %s", command.phoneNumber()));
        }

        if (isNull(command.username()) || command.username().isBlank()) {
            throw new IllegalArgumentException(format("Username can not be blank %s", command.username()));
        }

        if (isNull(command.password()) || command.password().isBlank()) {
            throw new IllegalArgumentException(format("Password can not be blank %s", command.password()));
        }
    }
}
