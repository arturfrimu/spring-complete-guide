package com.artur.springjpa.service;

public interface ChangeAccountNameUseCase {

    void change(Long accountId, String accountName);
}
