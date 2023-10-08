package com.artur.springjpa.service;

import com.artur.springjpa.entity.Account;
import com.artur.springjpa.repository.AccountRepository;
import com.artur.springjpa.service.command.CreateAccountCommand;
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
