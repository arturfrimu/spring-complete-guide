package com.arturfrimu.common.facade;

import com.arturfrimu.common.annotation.Facade;
import com.arturfrimu.common.service.scope.PrototypeService;
import com.arturfrimu.common.service.scope.RequestService;
import com.arturfrimu.common.service.scope.SessionService;
import com.arturfrimu.common.service.scope.SingletonService;
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

    public boolean equalSingletons() {
        return singletonService1.equals(singletonService2) && singletonService1.getId().equals(singletonService2.getId());
    }

    public boolean equalPrototypes() {
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
