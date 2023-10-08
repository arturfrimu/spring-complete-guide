package com.artur.springjpa.service;

public interface ChangeUserUsernameUseCase {

    void change(Long userId, String username);
}
