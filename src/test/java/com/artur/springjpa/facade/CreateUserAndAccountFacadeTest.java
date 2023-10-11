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
import com.artur.springjpa.service.command.UpdateAccoundNameCommand;
import com.artur.springjpa.service.command.UpdateUsernameCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.IllegalTransactionStateException;

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
        String username = "username";
        String accountName = "account";

        var createUserCommand = new CreateUserCommand("1234", username, "password");
        var createAccountCommand = new CreateAccountCommand(accountName);

        userAndAccountFacade.createUserAndAccountFacade(
                createUserCommand,
                createAccountCommand
        );

        assertThat(userRepository.findByPersonalInformationUsername(username)).isNotEmpty();
        assertThat(accountRepository.findByAccountName(accountName)).isNotEmpty();
    }

    @Test
    void createUserAndAccountPropagationRequiredFailed() {
        String username = "username";
        var createUserCommand = new CreateUserCommand("1234", username, "password");
        var createAccountCommand = new CreateAccountCommand(null);

        assertThrows(NullPointerException.class, () -> {
            userAndAccountFacade.createUserAndAccountFacade(
                    createUserCommand,
                    createAccountCommand
            );
        }, "Account name can't be null");

        assertThat(userRepository.findByPersonalInformationUsername(username)).isEmpty();
        assertThat(accountRepository.findAll()).isEmpty();
    }

    @Test
    void changeUsernameAndAccountNamePropagationRequiredNew() {
        var userId = 1L;
        long accountId = 1L;

        userRepository.save(new User(userId, "12345", new UserPersonalInformation("oldUsername", "oldPassword")));
        accountRepository.save(new Account(accountId, "oldAccountName"));

        userAndAccountFacade.changeUsernameAndAccountName(
                new UpdateUsernameCommand(userId, "newUsername"),
                new UpdateAccoundNameCommand(accountId, "newAccountName")
        );

        assertThat(userRepository.findById(userId).get().getPersonalInformation().getUsername()).isEqualTo("newUsername");
        assertThat(accountRepository.findById(accountId).get().getAccountName()).isEqualTo("newAccountName");
    }

    @Test
    void changeUsernameAndAccountNameIsNullPropagationRequiredNew() {
        var userId = 1L;
        var accountId = 1L;
        var wrongAccountId = -1L;

        userRepository.save(new User(userId, "12345", new UserPersonalInformation("oldUsername", "oldPassword")));
        accountRepository.save(new Account(accountId, "oldAccountName"));

        assertThrows(ResourceNotFoundException.class, () -> {
            userAndAccountFacade.changeUsernameAndAccountName(
                    new UpdateUsernameCommand(userId, "newUsername"),
                    new UpdateAccoundNameCommand(wrongAccountId, null)
            );
        }, "Account not found with id: %s".formatted(wrongAccountId));

        assertThat(userRepository.findById(userId).get().getPersonalInformation().getUsername()).isEqualTo("newUsername");
        assertThat(accountRepository.findById(accountId).get().getAccountName()).isEqualTo("oldAccountName");
    }

    @Test
    void upperCaseUsernameAndAccountNamePropagationMandatory() {
        var userId = 1L;
        var accountId = 1L;

        userRepository.saveAndFlush(new User(userId, "12345", new UserPersonalInformation("oldUsername", "oldPassword")));
        accountRepository.saveAndFlush(new Account(accountId, "oldAccountName"));

        userAndAccountFacade.makeUpperCaseUsernameAndAccountName(userId, accountId);

        assertThat(userRepository.findById(userId).get().getPersonalInformation().getUsername()).isEqualTo("oldUsername".toUpperCase());
        assertThat(accountRepository.findById(accountId).get().getAccountName()).isEqualTo("oldAccountName".toUpperCase());
    }

    @Test
    void lowerCaseUsernameAndAccountNamePropagationMandatoryFail() {
        var userId = 1L;
        var accountId = 1L;

        userRepository.save(new User(userId, "12345", new UserPersonalInformation("oldUsername", "oldPassword")));
        accountRepository.save(new Account(accountId, "oldAccountName"));

        assertThrows(IllegalTransactionStateException.class, () -> {
            userAndAccountFacade.makeLowerCaseUsernameAndAccountName(userId, accountId);
        }, "No existing transaction found for transaction marked with propagation 'mandatory'");

        assertThat(userRepository.findById(userId).get().getPersonalInformation().getUsername()).isEqualTo("oldUsername");
        assertThat(accountRepository.findById(accountId).get().getAccountName()).isEqualTo("oldAccountName");
    }

    /**
     * This method capitalizes the first letter of both the user's username and the account name.
     * The method operates within a transactional context defined by the SUPPORTS propagation behavior.
     *
     * In a technical perspective, the SUPPORTS propagation mode in Spring's transaction management
     * means the following:
     * - If there's an active transaction already present, the method will run within that transaction context.
     * - If there's no active transaction, the method will execute without a transaction. It won't start
     *   a new one but will rather continue executing in a non-transactional mode.
     *
     * Therefore, in this case, if an outer transactional method calls this method, the changes to the
     * user's username and account name will be part of the outer transaction. If the outer transaction
     * rolls back, so will the changes made by this method.
     * Conversely, if this method is called outside of any transaction, the database changes are executed
     * immediately, and they won't be part of any transaction, which gives an autosave-like behavior.
     */

    @Test
    void upperFirstLetterCaseUsernameAndAccountNamePropagationSupports() {
        var userId = 1L;
        var accountId = 1L;

        userRepository.save(new User(userId, "12345", new UserPersonalInformation("oldUsername", "oldPassword")));
        accountRepository.save(new Account(accountId, "oldAccountName"));

        userAndAccountFacade.makeFirstLetterUpperCaseUsernameAndAccountNameSuccess(userId, accountId);

        assertThat(userRepository.findById(userId).get().getPersonalInformation().getUsername()).isEqualTo("OldUsername");
        assertThat(accountRepository.findById(accountId).get().getAccountName()).isEqualTo("OldAccountName");
    }

    @Test
    void upperFirstLetterCaseUsernameAndAccountNamePropagationSupportsFail() {
        var userId = 1L;
        var accountId = 1L;

        userRepository.save(new User(userId, "12345", new UserPersonalInformation("oldUsername", "oldPassword")));
        accountRepository.save(new Account(accountId, "oldAccountName"));

        userAndAccountFacade.makeFirstLetterUpperCaseUsernameAndAccountNameFail(userId, accountId);

        assertThat(userRepository.findById(userId).get().getPersonalInformation().getUsername()).isEqualTo("oldUsername");
        assertThat(accountRepository.findById(accountId).get().getAccountName()).isEqualTo("OldAccountName");
    }
}