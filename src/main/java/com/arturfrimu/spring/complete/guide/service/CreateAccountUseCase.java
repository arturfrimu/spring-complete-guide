package com.arturfrimu.spring.complete.guide.service;

import com.arturfrimu.spring.complete.guide.service.command.CreateAccountCommand;

public interface CreateAccountUseCase {

    void create(CreateAccountCommand command);
}
