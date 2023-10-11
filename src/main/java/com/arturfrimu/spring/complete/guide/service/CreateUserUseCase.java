package com.arturfrimu.spring.complete.guide.service;

import com.arturfrimu.spring.complete.guide.service.command.CreateUserCommand;

public interface CreateUserUseCase {

    void create(CreateUserCommand command);
}
