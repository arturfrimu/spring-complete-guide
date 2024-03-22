package com.arturfrimu.common.service.command;

public record CreateUserCommand(
        String phoneNumber,
        String username,
        String password
) {
}
