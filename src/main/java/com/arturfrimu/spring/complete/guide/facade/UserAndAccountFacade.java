package com.arturfrimu.spring.complete.guide.facade;

import com.arturfrimu.spring.complete.guide.annotation.Facade;
import com.arturfrimu.spring.complete.guide.service.ChangeAccountNameService;
import com.arturfrimu.spring.complete.guide.service.ChangeUserUsernameService;
import com.arturfrimu.spring.complete.guide.service.CreateAccountUseCase;
import com.arturfrimu.spring.complete.guide.service.CreateUserUseCase;
import com.arturfrimu.spring.complete.guide.service.command.CreateAccountCommand;
import com.arturfrimu.spring.complete.guide.service.command.CreateUserCommand;
import com.arturfrimu.spring.complete.guide.service.command.UpdateAccoundNameCommand;
import com.arturfrimu.spring.complete.guide.service.command.UpdateUsernameCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Facade
@RequiredArgsConstructor
public class UserAndAccountFacade {

    private final CreateUserUseCase createUserUseCase;
    private final CreateAccountUseCase createAccountUseCase;

    /**
     * Handles the creation of a user and an associated account within a single transactional boundary.
     *
     * <p>This facade method ensures atomicity between user and account creation operations. If any
     * part of the process fails (either user creation or account creation), the entire transaction
     * will be rolled back, ensuring data consistency.</p>
     *
     * @param createUserCommand    Command object containing information required for creating a user.
     * @param createAccountCommand Command object containing information required for creating an account.
     * @throws NullPointerException if [specific condition related to user creation]
     * @throws NullPointerException if [specific condition related to account creation]
     * @see CreateUserUseCase#create(CreateUserCommand)
     * @see CreateAccountUseCase#create(CreateAccountCommand)
     */
    @Transactional
    public void createUserAndAccountFacade(
            CreateUserCommand createUserCommand,
            CreateAccountCommand createAccountCommand
    ) {
        createUserUseCase.create(createUserCommand);
        createAccountUseCase.create(createAccountCommand);
    }

    private final ChangeUserUsernameService changeUserUsernameService;
    private final ChangeAccountNameService changeAccountNameService;

    @Transactional
    public void changeUsernameAndAccountName(
            UpdateUsernameCommand updateUsernameCommand,
            UpdateAccoundNameCommand updateAccoundNameCommand
    ) {
        changeUserUsernameService.change(updateUsernameCommand.userId(), updateUsernameCommand.username());
        changeAccountNameService.change(updateAccoundNameCommand.accountId(), updateAccoundNameCommand.accountName());
    }

    @Transactional
    public void makeUpperCaseUsernameAndAccountName(final Long userId, final Long accountId) {
        changeUserUsernameService.makeUpperCaseUsername(userId);
        changeAccountNameService.makeUpperCaseAccountName(accountId);
    }

    public void makeLowerCaseUsernameAndAccountName(final Long userId, final Long accountId) {
        changeUserUsernameService.makeLowerCaseUsername(userId);
        changeAccountNameService.makeLowerCaseAccountName(accountId);
    }

    @Transactional
    public void makeFirstLetterUpperCaseUsernameAndAccountNameSuccess(final Long userId, final Long accountId) {
        changeUserUsernameService.makeFirstLetterUpperCaseUsername(userId);
        changeAccountNameService.makeFirstLetterLowerCaseAccountName(accountId);
    }

    public void makeFirstLetterUpperCaseUsernameAndAccountNameFail(final Long userId, final Long accountId) {
        changeUserUsernameService.makeFirstLetterUpperCaseUsername(userId);
        changeAccountNameService.makeFirstLetterLowerCaseAccountName(accountId);
    }
}