package com.artur.springjpa.facade;

import annotation.ServiceTest;
import com.artur.springjpa.repository.AccountRepository;
import com.artur.springjpa.repository.UserRepository;
import com.artur.springjpa.service.command.CreateAccountCommand;
import com.artur.springjpa.service.command.CreateUserCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ServiceTest
class CreateUserAndAccountFacadeTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    UserAndAccountFacade userAndAccountFacade;

    @BeforeEach
    void cleanUp() {
        userRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    void createUserAndAccountPropagationRequired() {
        var createUserCommand = new CreateUserCommand("1234", "username", "password");
        var createAccountCommand = new CreateAccountCommand("account");

        userAndAccountFacade.createUserAndAccountFacade(
                createUserCommand,
                createAccountCommand
        );

        assertThat(userRepository.findAll()).hasSize(1);
        assertThat(accountRepository.findAll()).hasSize(1);
    }

    @Test
    void createUserAndAccountPropagationRequiredFailed() {
        var createUserCommand = new CreateUserCommand("1234", "username", "password");
        var createAccountCommand = new CreateAccountCommand(null);

        assertThrows(NullPointerException.class, () -> {
            userAndAccountFacade.createUserAndAccountFacade(
                    createUserCommand,
                    createAccountCommand
            );
        }, "Account name can't be null");

        assertThat(userRepository.findAll()).isEmpty();
        assertThat(accountRepository.findAll()).isEmpty();
    }
}