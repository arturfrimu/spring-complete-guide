package com.arturfrimu.common.controller;

import com.arturfrimu.common.facade.ScopeFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scopes")
@RequiredArgsConstructor
public class ScopeController {

    private final ScopeFacade scopeFacade;

    @GetMapping("/request/makeRequest")
    public void incrementRequest() {
        scopeFacade.incrementRequest();
    }

    @GetMapping("/request/count")
    public int requestBeanCount() {
        return scopeFacade.requestBeanCount();
    }

    @GetMapping("/session/makeRequest")
    public void incrementSession() {
        scopeFacade.incrementSession();
    }

    @GetMapping("/session/count")
    public int sessionBeanCount() {
        return scopeFacade.sessionBeanCount();
    }
}
