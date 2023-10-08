package com.artur.springjpa.service;

import com.artur.springjpa.service.command.CreateAccountCommand;

public interface CreateAccountUseCase {

    void create(CreateAccountCommand command);
}
