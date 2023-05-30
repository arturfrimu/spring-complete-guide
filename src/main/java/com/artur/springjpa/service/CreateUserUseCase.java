package com.artur.springjpa.service;

import com.artur.springjpa.service.command.CreateUserCommand;

public interface CreateUserUseCase {

    void create(CreateUserCommand command);
}
