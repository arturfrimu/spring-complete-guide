package com.artur.springjpa.facade;

public record UpdateUsernameCommand(Long userId, String username) {
}