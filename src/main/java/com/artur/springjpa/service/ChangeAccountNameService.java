package com.artur.springjpa.service;

import com.artur.springjpa.entity.Account;
import com.artur.springjpa.exception.ResourceNotFoundException;
import com.artur.springjpa.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional
    public void makeUpperCaseAccountName(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: %s".formatted(accountId)));

        account.setAccountName(account.getAccountName().toUpperCase());
    }

    @Override
    @Transactional
    public void makeLowerCaseAccountName(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: %s".formatted(accountId)));

        account.setAccountName(account.getAccountName().toLowerCase());
    }

    @Override
    @Transactional
    public void makeFirstLetterLowerCaseAccountName(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: %s".formatted(accountId)));

        String capitalizeFirstLetter = capitalizeFirstLetter(account.getAccountName());
        account.setAccountName(capitalizeFirstLetter);
    }

    private static String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }
}
