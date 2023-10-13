package com.arturfrimu.spring.complete.guide.facade;

import com.arturfrimu.spring.complete.guide.annotation.Facade;
import com.arturfrimu.spring.complete.guide.service.PrototypeService;
import com.arturfrimu.spring.complete.guide.service.SingletonService;
import lombok.RequiredArgsConstructor;

@Facade
@RequiredArgsConstructor
public class ScopeFacade {

    private final SingletonService singletonService1;
    private final SingletonService singletonService2;
    private final PrototypeService prototypeService1;
    private final PrototypeService prototypeService2;

    boolean equalSingletons() {
        return singletonService1.equals(singletonService2);
    }

    boolean equalPrototypes() {
        return prototypeService1.equals(prototypeService2);
    }
}
