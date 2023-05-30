package com.artur.springjpa.service.command;

public record CreateUserCommand(String phoneNumber, String username, String password) {
}
