package com.arturfrimu.common.repository;

import com.arturfrimu.common.entity.Account;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    void findByAccountNameTest() {
        List<Account> savedAccounts = IntStream.range(0, 1_000).mapToObj(index -> accountRepository.save(Account.builder()
                        .accountName("name-%s".formatted(RandomStringUtils.randomAlphabetic(5)))
                        .build()))
                .toList();

        int randomIndex = ThreadLocalRandom
                .current()
                .nextInt(savedAccounts.size());

        String searchByName = savedAccounts.get(randomIndex).getAccountName();
        Account account = accountRepository.findByAccountName(searchByName)
                .orElseThrow();

        assertThat(account.getAccountName()).isEqualTo(searchByName);
    }
}