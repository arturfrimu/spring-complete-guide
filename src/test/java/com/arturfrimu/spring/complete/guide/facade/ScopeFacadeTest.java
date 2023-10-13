package com.arturfrimu.spring.complete.guide.facade;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ScopeFacadeTest {

    @Autowired
    private ScopeFacade scopeFacade;

    @Test
    void equalSingletons() {
        assertTrue(scopeFacade.equalSingletons());
    }

    @Test
    void equalPrototypes() {
        assertFalse(scopeFacade.equalPrototypes());
    }
}