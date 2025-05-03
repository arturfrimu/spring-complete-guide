package com.arturfrimu;

import com.arturfrimu.fixedwindow.FixedWindowRateLimiter;
import com.arturfrimu.leakybucket.LeakyBucketRateLimiter;
import com.arturfrimu.slidingwindow.SlidingWindowLogRateLimiter;
import com.arturfrimu.slidingwindowcounter.SlidingWindowCounterRateLimiter;
import com.arturfrimu.tokenbucketr.TokenBucketRateLimiter;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RateLimiterTest {

    private static final List<RateLimiter> rateLimiters = List.of(
            new FixedWindowRateLimiter(2, 1000),
            new LeakyBucketRateLimiter(2, 1000),
            new TokenBucketRateLimiter(2, 1000),
            new SlidingWindowCounterRateLimiter(2, 1000, 1),
            new SlidingWindowLogRateLimiter(2, 1000)
    );

    @Test
    void testAllowRequestsUnderLimit() {
        IntStream.range(0, rateLimiters.size())
                .forEach(index -> {
                    RateLimiter limiter = rateLimiters.get(index);

                    System.out.println(limiter.getClass().getSimpleName());

                    String clientId = UUID.randomUUID().toString();

                    boolean first = limiter.allowRequest(clientId);
                    boolean second = limiter.allowRequest(clientId);
                    boolean third = limiter.allowRequest(clientId);

                    assertTrue(first, "1st request should be allowed");
                    assertTrue(second, "2nd request should be allowed");
                    assertFalse(third, "3rd request should not be allowed");
                });
    }
}