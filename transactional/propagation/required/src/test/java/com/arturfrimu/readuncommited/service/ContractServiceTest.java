package com.arturfrimu.readuncommited.service;

import com.arturfrimu.readuncommited.repository.ContractRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class ContractServiceTest {

    @Autowired
    private ContractService contractService;
    @Autowired
    private ContractRepository contractRepository;

    @BeforeEach
    @AfterEach
    void setUp() {
        contractRepository.deleteAll();
    }

    @Test
    void required1() {
        assertThatThrownBy(() -> contractService.required());
        assertThat(contractRepository.findAll()).hasSize(0);
    }

    @Test
    void required2() {
        assertThatThrownBy(() -> contractService.required2());
        assertThat(contractRepository.findAll()).hasSize(0);
    }

    @Test
    void required3() {
        contractService.required3();
        assertThat(contractRepository.findAll()).hasSize(2);
    }
}