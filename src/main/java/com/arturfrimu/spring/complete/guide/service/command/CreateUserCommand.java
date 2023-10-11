package com.arturfrimu.spring.complete.guide.service.command;

public record CreateUserCommand(
        String phoneNumber,
        String username,
        String password
) {
}
