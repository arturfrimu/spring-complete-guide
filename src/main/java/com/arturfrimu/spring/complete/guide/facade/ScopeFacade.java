package com.arturfrimu.spring.complete.guide.facade;

import com.arturfrimu.spring.complete.guide.annotation.Facade;
import com.arturfrimu.spring.complete.guide.service.scope.PrototypeService;
import com.arturfrimu.spring.complete.guide.service.scope.RequestService;
import com.arturfrimu.spring.complete.guide.service.scope.SessionService;
import com.arturfrimu.spring.complete.guide.service.scope.SingletonService;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Facade
@RequiredArgsConstructor
public class ScopeFacade {

    private final Set<UUID> requestScope = new HashSet<>();
    private final Set<UUID> sessionScope = new HashSet<>();

    private final SingletonService singletonService1;
    private final SingletonService singletonService2;
    private final PrototypeService prototypeService1;
    private final PrototypeService prototypeService2;

    private final RequestService requestService;

    private final SessionService sessionService;

    boolean equalSingletons() {
        return singletonService1.equals(singletonService2) && singletonService1.getId().equals(singletonService2.getId());
    }

    boolean equalPrototypes() {
        return prototypeService1.equals(prototypeService2) && prototypeService1.getId().equals(prototypeService2.getId());
    }

    public void incrementRequest() {
        requestScope.add(requestService.getId());
    }

    public int requestBeanCount() {
        return requestScope.size();
    }

    public void incrementSession() {
        sessionScope.add(sessionService.getId());
    }

    public int sessionBeanCount() {
        return sessionScope.size();
    }
}
