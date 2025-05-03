package com.arturfrimu;

public interface RateLimiter {

    boolean allowRequest(String value);
}
