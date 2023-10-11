package com.artur.springjpa.service.command;

public record UpdateUsernameCommand(Long userId, String username) {
}