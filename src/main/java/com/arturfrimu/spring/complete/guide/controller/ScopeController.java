package com.arturfrimu.spring.complete.guide.controller;

import com.arturfrimu.spring.complete.guide.facade.ScopeFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scopes")
@RequiredArgsConstructor
public class ScopeController {

    private final ScopeFacade scopeFacade;

    @GetMapping("/makeRequest")
    public void makeRequest() {
        scopeFacade.add();
    }

    @GetMapping("/count")
    public int isTheSameRequest() {
        return scopeFacade.size();
    }
}
