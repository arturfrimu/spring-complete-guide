package com.arturfrimu.spring.complete.guide.service.transactional.propagation;

import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Propagation.MANDATORY;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

public interface ChangeUserUsernameUseCase {

    void change(Long userId, String username);

    @Transactional(propagation = REQUIRES_NEW)
    void makeUpperCaseUsername(Long userId);

    @Transactional(propagation = MANDATORY)
    void makeLowerCaseUsername(Long userId);

    @Transactional(propagation = MANDATORY)
    void makeFirstLetterUpperCaseUsername(Long userId);
}
