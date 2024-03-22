package com.arturfrimu.common.service.transactional.propagation;

import com.arturfrimu.common.entity.Account;
import com.arturfrimu.common.repository.AccountRepository;
import com.arturfrimu.common.service.command.CreateAccountCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateAccountService implements CreateAccountUseCase {

    private final AccountRepository accountRepository;

    @Override
    public void create(CreateAccountCommand command) {
        accountRepository.save(new Account(command.accountName()));
    }
}
