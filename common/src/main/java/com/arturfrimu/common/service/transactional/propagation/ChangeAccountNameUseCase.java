package com.arturfrimu.common.service.transactional.propagation;

import org.springframework.transaction.annotation.Transactional;

public interface ChangeAccountNameUseCase {

    void change(Long accountId, String accountName);

    @Transactional
    void makeUpperCaseAccountName(Long accountId);

    @Transactional
    void makeLowerCaseAccountName(Long accountId);

    @Transactional
    void makeFirstLetterLowerCaseAccountName(Long accountId);
}
