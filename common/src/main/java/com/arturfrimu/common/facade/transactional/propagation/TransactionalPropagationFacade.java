package com.arturfrimu.common.facade.transactional.propagation;

import com.arturfrimu.common.annotation.Facade;
import com.arturfrimu.common.service.command.CreateAccountCommand;
import com.arturfrimu.common.service.command.CreateUserCommand;
import com.arturfrimu.common.service.command.UpdateAccoundNameCommand;
import com.arturfrimu.common.service.command.UpdateUsernameCommand;
import com.arturfrimu.common.service.transactional.propagation.ChangeAccountNameService;
import com.arturfrimu.common.service.transactional.propagation.ChangeUserUsernameService;
import com.arturfrimu.common.service.transactional.propagation.CreateAccountUseCase;
import com.arturfrimu.common.service.transactional.propagation.CreateUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Facade
@RequiredArgsConstructor
public class TransactionalPropagationFacade {

    private final CreateUserUseCase createUserUseCase;
    private final CreateAccountUseCase createAccountUseCase;
    private final ChangeUserUsernameService changeUserUsernameService;
    private final ChangeAccountNameService changeAccountNameService;

    @Transactional
    public void createUserAndAccountFacade(
            CreateUserCommand createUserCommand,
            CreateAccountCommand createAccountCommand
    ) {
        createUserUseCase.create(createUserCommand);
        createAccountUseCase.create(createAccountCommand);
    }

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
