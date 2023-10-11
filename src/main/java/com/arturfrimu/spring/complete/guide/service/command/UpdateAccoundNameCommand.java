package com.arturfrimu.spring.complete.guide.service.command;

public record UpdateAccoundNameCommand(Long accountId, String accountName) {
}