package com.arturfrimu.spring.complete.guide.service.transactional.propagation;

import com.arturfrimu.spring.complete.guide.entity.Account;
import com.arturfrimu.spring.complete.guide.repository.AccountRepository;
import com.arturfrimu.spring.complete.guide.service.command.CreateAccountCommand;
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
