package com.arturfrimu.common.service.transactional.propagation;


import com.arturfrimu.common.service.command.CreateUserCommand;

public interface CreateUserUseCase {

    void create(CreateUserCommand command);
}
