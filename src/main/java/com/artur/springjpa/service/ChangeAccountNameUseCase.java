package com.artur.springjpa.service;

import org.springframework.transaction.annotation.Transactional;

public interface ChangeAccountNameUseCase {

    void change(Long accountId, String accountName);

    @Transactional
    void makeUpperCaseAccountName(Long accountId);

    @Transactional
    void makeLowerCaseAccountName(Long accountId);
}
