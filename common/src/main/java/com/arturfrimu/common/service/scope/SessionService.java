package com.arturfrimu.common.service.scope;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.springframework.context.annotation.ScopedProxyMode.TARGET_CLASS;

@Slf4j
@Service
@Scope(value = "session", proxyMode = TARGET_CLASS)
@EqualsAndHashCode
@Getter
public class SessionService {

    private final UUID id = UUID.randomUUID();
}
