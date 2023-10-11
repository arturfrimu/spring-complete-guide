package com.artur.springjpa.service.command;

public record UpdateAccoundNameCommand(Long accountId, String accountName) {
}