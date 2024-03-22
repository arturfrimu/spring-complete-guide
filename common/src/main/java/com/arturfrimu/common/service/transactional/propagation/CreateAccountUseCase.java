package com.arturfrimu.common.service.transactional.propagation;


import com.arturfrimu.common.service.command.CreateAccountCommand;

public interface CreateAccountUseCase {

    void create(CreateAccountCommand command);
}
