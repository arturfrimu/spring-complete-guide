package com.artur.springjpa.service;

import com.artur.springjpa.entity.Account;
import com.artur.springjpa.exception.ResourceNotFoundException;
import com.artur.springjpa.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Service
@RequiredArgsConstructor
public class ChangeAccountNameService implements ChangeAccountNameUseCase {

    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public void change(Long accountId, String accountName) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: %s".formatted(accountId)));

        account.setAccountName(accountName);
    }
}
