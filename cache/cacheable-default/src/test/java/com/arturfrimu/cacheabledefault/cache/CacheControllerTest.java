package com.arturfrimu.cacheabledefault.cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.function.Supplier;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link CacheController}.
 * These tests are aimed at verifying the behavior of the caching mechanism in the application.
 * Specifically, it tests the cache retrieval performance, ensuring that once an item is cached,
 * subsequent retrievals are significantly faster than the initial retrieval.
 * The tests also ensure that the cache behaves independently across different application instances
 * by invoking the same cacheable method on different ports and observing the expected cache miss and hit behavior.
 * The {@link #evictCache()} method is used to clear the cache before each test to ensure test isolation and predictability.<br><br><br>
 * Start 2 apps with next ports 8080, 8081
 */
@SpringBootTest(webEnvironment = RANDOM_PORT)
class CacheControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    private static final String URL_CACHEABLE = "http://localhost:%d/api/v1/cache/default/%s";
    private static final String FIRST_APP_URL = URL_CACHEABLE.formatted(8080, "1");
    private static final String SECOND_APP_URL = URL_CACHEABLE.formatted(8081, "1");

    private static final String URL_EVICT = "http://localhost:%d/api/v1/cache/default/evict";

    /**
     * Clears the cache before each test execution to ensure a clean state.
     * This is crucial for testing cache behavior as it ensures the cache is empty at the start of each test,
     * allowing for accurate measurement of cache miss and hit scenarios.
     */
    @BeforeEach
    void evictCache() {
        testRestTemplate.getForEntity(URL_EVICT.formatted(8080), String.class);
        testRestTemplate.getForEntity(URL_EVICT.formatted(8081), String.class);
    }

    /**
     * Tests the default caching behavior by verifying the response time of cacheable method invocations.
     * The first call to a cacheable method is expected to take longer (cache miss),
     * while subsequent calls should be significantly faster (cache hit).
     *
     * This test also verifies that the cache is local to each application instance by demonstrating
     * that a cache hit in one instance does not lead to a cache hit in another instance,
     * which simulates the behavior in a distributed or scaled-up application environment.
     */
    @Test
    void testDefaultCache() {
        assertThat(measure(() -> testRestTemplate.getForEntity(FIRST_APP_URL, String.class)))
                .isGreaterThanOrEqualTo(2000L); // 2000ms

        assertThat(measure(() -> testRestTemplate.getForEntity(FIRST_APP_URL, String.class)))
                .isLessThanOrEqualTo(10L); // 2ms

        assertThat(measure(() -> testRestTemplate.getForEntity(FIRST_APP_URL, String.class)))
                .isLessThanOrEqualTo(10L); // 2ms

        assertThat(measure(() -> testRestTemplate.getForEntity(SECOND_APP_URL, String.class)))
                .isGreaterThanOrEqualTo(2000L); // 2000ms

        assertThat(measure(() -> testRestTemplate.getForEntity(SECOND_APP_URL, String.class)))
                .isLessThanOrEqualTo(10L); // 2ms

        assertThat(measure(() -> testRestTemplate.getForEntity(SECOND_APP_URL, String.class)))
                .isLessThanOrEqualTo(10L); // 2ms
    }

    private long measure(Supplier<Object> supplier) {
        long start = System.currentTimeMillis();
        supplier.get();
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        return end - start;
    }
}