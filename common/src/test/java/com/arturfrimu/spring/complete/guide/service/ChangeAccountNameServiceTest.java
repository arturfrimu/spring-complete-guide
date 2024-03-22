package com.arturfrimu.spring.complete.guide.service;

import annotation.ServiceTest;
import com.arturfrimu.common.entity.Account;
import com.arturfrimu.common.repository.AccountRepository;
import com.arturfrimu.common.service.transactional.propagation.ChangeAccountNameUseCase;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

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