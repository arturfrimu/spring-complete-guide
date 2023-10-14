package com.arturfrimu.spring.complete.guide.service;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.springframework.context.annotation.ScopedProxyMode.TARGET_CLASS;

@Slf4j
@Service
@Scope(value = "request", proxyMode = TARGET_CLASS)
@EqualsAndHashCode
@Getter
public class RequestService {

    private final UUID id = UUID.randomUUID();
}
