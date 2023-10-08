package com.artur.springjpa.service;

import com.artur.springjpa.annotation.Facade;
import com.artur.springjpa.service.command.CreateAccountCommand;
import com.artur.springjpa.service.command.CreateUserCommand;
import lombok.RequiredArgsConstructor;

@Facade
@RequiredArgsConstructor
public class CreateUserAndAccountFacade {

    private final CreateUserUseCase createUserUseCase;
    private final CreateAccountUseCase createAccountUseCase;

    public void createUserAndAccountFacade(
            CreateUserCommand createUserCommand,
            CreateAccountCommand createAccountCommand
    ) {
        createUserUseCase.create(createUserCommand);
        createAccountUseCase.create(createAccountCommand);
    }
}
