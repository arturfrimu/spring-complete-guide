package com.arturfrimu.spring.complete.guide.service;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@EqualsAndHashCode
@Getter
public class ApplicationService {

    private final UUID id = UUID.randomUUID();
}