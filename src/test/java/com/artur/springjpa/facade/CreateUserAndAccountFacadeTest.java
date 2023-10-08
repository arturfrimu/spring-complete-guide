package com.artur.springjpa.facade;

import annotation.ServiceTest;
import com.artur.springjpa.entity.Account;
import com.artur.springjpa.entity.User;
import com.artur.springjpa.entity.UserPersonalInformation;
import com.artur.springjpa.exception.ResourceNotFoundException;
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

    @Test
    void changeUsernameAndAccountName() {
        userRepository.save(new User(1L, "12345", new UserPersonalInformation("oldUsername", "oldPassword")));
        accountRepository.save(new Account(1L, "oldAccountName"));

        userAndAccountFacade.changeUsernameAndAccountName(
                1L, "newUsername", 1L, "newAccountName"
        );

        assertThat(userRepository.findById(1L).get().getPersonalInformation().getUsername()).isEqualTo("newUsername");
        assertThat(accountRepository.findById(1L).get().getAccountName()).isEqualTo("newAccountName");
    }

    @Test
    void changeUsernameAndAccountNameIsNull() {
        userRepository.save(new User(1L, "12345", new UserPersonalInformation("oldUsername", "oldPassword")));
        accountRepository.save(new Account(1L, "oldAccountName"));

        assertThrows(ResourceNotFoundException.class, () -> {
            userAndAccountFacade.changeUsernameAndAccountName(
                    1L, "newUsername", 2L, null
            );
        }, "Account not found with id: %s".formatted(2L));

        assertThat(userRepository.findById(1L).get().getPersonalInformation().getUsername()).isEqualTo("newUsername");
        assertThat(accountRepository.findById(1L).get().getAccountName()).isEqualTo("oldAccountName");
    }
}