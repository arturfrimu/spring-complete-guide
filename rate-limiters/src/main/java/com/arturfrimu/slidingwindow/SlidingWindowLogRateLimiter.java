package com.arturfrimu.slidingwindow;

import com.arturfrimu.RateLimiter;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * A simple Fixed Window Rate Limiter.
 * Tracks counts of events per clientId in fixed time windows.
 */

/**
 * A simple Sliding Window Log Rate Limiter.
 * <p>
 * Stores timestamps of each request and evicts those older than the window.
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SlidingWindowLogRateLimiter implements RateLimiter {

    /**
     * Maximum requests allowed per window
     */
    private final int maxRequests;
    /**
     * Window size in milliseconds
     */
    private final long windowSizeInMillis;

    /**
     * Holds per-client request timestamps.
     */
    private final ConcurrentMap<String, Deque<Long>> requestLogs = new ConcurrentHashMap<>();

    /**
     * @param maxRequests        maximum number of requests allowed per window
     * @param windowSizeInMillis duration of each sliding window in milliseconds
     */
    public SlidingWindowLogRateLimiter(int maxRequests, long windowSizeInMillis) {
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
        Deque<Long> timestamps = requestLogs.computeIfAbsent(clientId, id -> new LinkedList<>());

        synchronized (timestamps) {
            // Remove timestamps older than the window
            long cutoff = now - windowSizeInMillis;
            while (!timestamps.isEmpty() && timestamps.peekFirst() <= cutoff) {
                timestamps.removeFirst();
            }
            if (timestamps.size() < maxRequests) {
                timestamps.addLast(now);
                return true;
            } else {
                return false;
            }
        }
    }
}


