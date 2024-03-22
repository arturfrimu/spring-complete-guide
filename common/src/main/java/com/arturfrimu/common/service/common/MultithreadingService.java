package com.arturfrimu.common.service.common;

public class MultithreadingService {

    public static void sleep(final long millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
