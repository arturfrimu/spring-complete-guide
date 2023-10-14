package com.arturfrimu.spring.complete.guide.facade;

import com.arturfrimu.spring.complete.guide.annotation.Facade;
import com.arturfrimu.spring.complete.guide.service.PrototypeService;
import com.arturfrimu.spring.complete.guide.service.RequestService;
import com.arturfrimu.spring.complete.guide.service.SingletonService;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Facade
@RequiredArgsConstructor
public class ScopeFacade {

    private final Set<UUID> requestScope = new HashSet<>();

    private final SingletonService singletonService1;
    private final SingletonService singletonService2;
    private final PrototypeService prototypeService1;
    private final PrototypeService prototypeService2;

    private final RequestService requestService;

    boolean equalSingletons() {
        return singletonService1.equals(singletonService2) && singletonService1.getId().equals(singletonService2.getId());
    }

    boolean equalPrototypes() {
        return prototypeService1.equals(prototypeService2);
    }

    public void add() {
        requestScope.add(requestService.getId());
    }

    public int size() {
        return requestScope.size();
    }
}
