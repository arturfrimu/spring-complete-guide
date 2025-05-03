package com.arturfrimu.logtime.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class LogTimeAspect {

    @Around("@annotation(com.arturfrimu.logtime.annotation.LogTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.nanoTime();
        try {
            return joinPoint.proceed();
        } finally {
            long duration = System.nanoTime() - start;
            double millis = duration / 1_000_000.0;
            log.info("Metoda {} a durat {} ms", joinPoint.getSignature().toShortString(), String.format("%.3f", millis));
        }
    }
}
