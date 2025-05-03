package com.arturfrimu.fixedwindow;

import com.arturfrimu.RateLimiter;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A simple Fixed Window Rate Limiter.
 * Tracks counts of events per clientId in fixed time windows.
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FixedWindowRateLimiter implements RateLimiter {

    /**
     * Maximum requests allowed per window
     */
    int maxRequests;

    /**
     * Window size in milliseconds
     */
    long windowSizeInMillis;

    /**
     * Holds per-client window state.
     */
    private static class Window {
        /**
         * window start timestamp
         */
        volatile long windowStart;
        /**
         * count of requests in current window
         */
        final AtomicInteger count = new AtomicInteger(0);

        Window(long windowStart) {
            this.windowStart = windowStart;
        }
    }

    /**
     * Map from client identifier to its Window
     */
    ConcurrentMap<String, Window> windows = new ConcurrentHashMap<>();

    /**
     * @param maxRequests        maximum number of requests allowed per window
     * @param windowSizeInMillis duration of each window in milliseconds
     */
    public FixedWindowRateLimiter(int maxRequests, long windowSizeInMillis) {
        this.maxRequests = maxRequests;
        this.windowSizeInMillis = windowSizeInMillis;
    }

    /**
     * Attempt to record a request for the given clientId.
     *
     * @param clientId unique identifier for the caller (e.g., IP, API key, user ID)
     * @return true if request is within the rate limit, false if it exceeds the limit
     */
    @Override
    public boolean allowRequest(String clientId) {
        long now = System.currentTimeMillis();

        // get or create the Window for this client
        Window window = windows.computeIfAbsent(clientId, id -> new Window(now));

        synchronized (window) {
            // if current window has expired, reset it
            if (now - window.windowStart >= windowSizeInMillis) {
                window.windowStart = now;
                window.count.set(0);
            }

            // increment count and check limit
            int currentCount = window.count.incrementAndGet();
            return currentCount <= maxRequests;
        }
    }
}

