package com.arturfrimu.common.service.command;

public record UpdateUsernameCommand(Long userId, String username) {
}