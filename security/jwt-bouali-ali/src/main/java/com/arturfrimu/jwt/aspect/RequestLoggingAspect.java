package com.arturfrimu.jwt.aspect;

import com.arturfrimu.jwt.config.HttpServletRequestLogger;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class RequestLoggingAspect {

    @Value("${aspect.logging.request.enabled}")
    private Boolean requestLoggingEnabled;

    private final HttpServletRequestLogger httpServletRequestLogger;

    @Before("execution(* com.arturfrimu.jwt.controller..*(..)) && (" +
            "@annotation(org.springframework.web.bind.annotation.GetMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PostMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.DeleteMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PutMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.RequestMapping))")
    public void logRequest() {
        if (requestLoggingEnabled) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            httpServletRequestLogger.logRequestDetails(request);
        }
    }
}
