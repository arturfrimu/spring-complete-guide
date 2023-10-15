package com.arturfrimu.spring.complete.guide.service.scope;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@Scope("prototype")
@EqualsAndHashCode
@Getter
public class PrototypeService {

    private final UUID id = UUID.randomUUID();
}
