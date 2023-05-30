package com.artur.springjpa.service.random;

import com.artur.springjpa.service.command.CreateUserCommand;
import lombok.Builder;
import lombok.Builder.Default;

import java.util.function.Supplier;

@Builder
public class RandomCreateUserCommand implements Supplier<CreateUserCommand> {

    @Default
    private final String phoneNumber = "123-45-67-89";
    @Default
    private final String username = "test";
    @Default
    private final String password = "test";

    @Override
    public CreateUserCommand get() {
        return new CreateUserCommand(phoneNumber, username, password);
    }
}
