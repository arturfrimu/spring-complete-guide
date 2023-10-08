package com.artur.springjpa.service;

import annotation.ServiceTest;
import com.artur.springjpa.entity.Account;
import com.artur.springjpa.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ServiceTest
class ChangeAccountNameServiceTest {

    @Autowired
    ChangeAccountNameUseCase changeAccountNameUseCase;
    @Autowired
    AccountRepository accountRepository;

    @Test
    void change() {
        accountRepository.save(new Account("oldAccountName"));

        changeAccountNameUseCase.change(1L, "newAccountName");

        assertThat(accountRepository.findById(1L).get().getAccountName()).isEqualTo("newAccountName");
    }
}