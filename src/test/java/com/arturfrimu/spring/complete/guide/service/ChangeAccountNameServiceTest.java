package com.arturfrimu.spring.complete.guide.service;

import annotation.ServiceTest;
import com.arturfrimu.spring.complete.guide.entity.Account;
import com.arturfrimu.spring.complete.guide.repository.AccountRepository;
import com.arturfrimu.spring.complete.guide.service.transactional.propagation.ChangeAccountNameUseCase;
import org.assertj.core.api.AssertionsForClassTypes;
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

        AssertionsForClassTypes.assertThat(accountRepository.findById(1L).get().getAccountName()).isEqualTo("newAccountName");
    }
}