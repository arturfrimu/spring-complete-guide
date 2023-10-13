package com.arturfrimu.spring.complete.guide.facade;

import com.arturfrimu.spring.complete.guide.annotation.Facade;
import com.arturfrimu.spring.complete.guide.service.SingletonService;
import lombok.RequiredArgsConstructor;

@Facade
@RequiredArgsConstructor
public class ScopeFacade {

    private final SingletonService singletonService1;
    private final SingletonService singletonService2;

    boolean equalSingletons() {
        return singletonService1.equals(singletonService2);
    }
}
