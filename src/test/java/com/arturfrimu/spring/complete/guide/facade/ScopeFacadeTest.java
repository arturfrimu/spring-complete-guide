package com.arturfrimu.spring.complete.guide.facade;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ScopeFacadeTest {

    @Autowired
    private MockMvc mockMvc;

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

    @Test
    void countRequestScopes() throws Exception {
        mockMvc.perform(get("/scopes/request/makeRequest")).andExpect(status().isOk());
        mockMvc.perform(get("/scopes/request/makeRequest")).andExpect(status().isOk());

        var response = mockMvc.perform(get("/scopes/request/count")).andExpect(status().isOk()).andReturn();

        int count = Integer.parseInt(response.getResponse().getContentAsString());

        assertThat(response).isNotNull();
        assertEquals(2, count);
    }
}