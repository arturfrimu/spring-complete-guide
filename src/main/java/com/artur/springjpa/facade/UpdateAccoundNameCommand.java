package com.artur.springjpa.facade;

public record UpdateAccoundNameCommand(Long accountId, String accountName) {
}