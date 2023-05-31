package com.artur.springjpa.service;

import com.artur.springjpa.entity.User;
import com.artur.springjpa.entity.UserPersonalInformation;
import com.artur.springjpa.repository.UserRepository;
import com.artur.springjpa.service.command.CreateUserCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static java.lang.String.format;
import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class CreateUserService implements CreateUserUseCase {

    private final UserRepository userRepository;

    @Override
    public void create(CreateUserCommand command) {
        validate(command);
        var newUser = toUser(command);
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

    private static User toUser(CreateUserCommand command) {
        return new User(command.phoneNumber(), new UserPersonalInformation(command.username(), command.password()));
    }
}
