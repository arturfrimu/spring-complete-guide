package com.arturfrimu.leakybucket;

import com.arturfrimu.RateLimiter;

public class LeakyBucketRateLimiter implements RateLimiter {
    private final long capacity;
    private final double leakTokensPerMillis;
    private double water;
    private long lastLeakTimestamp;

    public LeakyBucketRateLimiter(long capacity, double leakRatePerSecond) {
        this.capacity = capacity;
        this.leakTokensPerMillis = leakRatePerSecond / 1000.0;
        this.water = 0;
        this.lastLeakTimestamp = System.currentTimeMillis();
    }

    @Override
    public synchronized boolean allowRequest(String key) {
        long now = System.currentTimeMillis();
        long elapsed = now - lastLeakTimestamp;
        double leaked = elapsed * leakTokensPerMillis;
        water = Math.max(0, water - leaked);
        lastLeakTimestamp = now;
        if (water < capacity) {
            water += 1;
            return true;
        }
        return false;
    }
}

