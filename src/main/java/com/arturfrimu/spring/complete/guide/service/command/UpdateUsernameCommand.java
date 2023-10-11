package com.arturfrimu.spring.complete.guide.service.command;

public record UpdateUsernameCommand(Long userId, String username) {
}