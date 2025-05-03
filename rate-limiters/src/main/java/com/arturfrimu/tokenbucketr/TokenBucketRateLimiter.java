package com.arturfrimu.tokenbucketr;


import com.arturfrimu.RateLimiter;

public class TokenBucketRateLimiter implements RateLimiter {
    private final long capacity;
    private final double refillTokensPerMillis;
    private double tokens;
    private long lastRefillTimestamp;

    public TokenBucketRateLimiter(long capacity, double refillRatePerSecond) {
        this.capacity = capacity;
        this.refillTokensPerMillis = refillRatePerSecond / 1000.0;
        this.tokens = capacity;
        this.lastRefillTimestamp = System.currentTimeMillis();
    }

    @Override
    public synchronized boolean allowRequest(String key) {
        long now = System.currentTimeMillis();
        long elapsed = now - lastRefillTimestamp;
        double refill = elapsed * refillTokensPerMillis;
        tokens = Math.min(capacity, tokens + refill);
        lastRefillTimestamp = now;
        if (tokens >= 1) {
            tokens -= 1;
            return true;
        }
        return false;
    }
}

