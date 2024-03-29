package com.arturfrimu.cacheabledefault.cache;

import com.arturfrimu.cacheabledefault.cache.CacheService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CacheServiceTest {

    @Autowired
    private CacheService cacheService;

    @BeforeEach
    void clearCache() {
        cacheService.cacheEvict(); // Clear cache before each test
    }

    /**
     * Validates the caching behavior of {@link CacheService} by measuring the retrieval time for the same data keys.
     * The first access for a unique key is expected to take over 2000ms, simulating a time-consuming operation.
     * Subsequent accesses should be under 2ms, indicating effective caching.
     */
    @Test
    void testCacheable() {
        assertThat(measure(() -> cacheService.getData("1"))).isGreaterThanOrEqualTo(2000L); // 2000ms
        assertThat(measure(() -> cacheService.getData("1"))).isLessThanOrEqualTo(2L); // 2ms
        assertThat(measure(() -> cacheService.getData("1"))).isLessThanOrEqualTo(2L); // 2ms

        assertThat(measure(() -> cacheService.getData("2"))).isGreaterThanOrEqualTo(2000L); // 2000ms
        assertThat(measure(() -> cacheService.getData("2"))).isLessThanOrEqualTo(2L); // 2ms
        assertThat(measure(() -> cacheService.getData("2"))).isLessThanOrEqualTo(2L); // 2ms

        assertThat(measure(() -> cacheService.getData("3"))).isGreaterThanOrEqualTo(2000L); // 2000ms
        assertThat(measure(() -> cacheService.getData("3"))).isLessThanOrEqualTo(2L); // 2ms
        assertThat(measure(() -> cacheService.getData("3"))).isLessThanOrEqualTo(2L); // 2ms
    }

    /**
     * Tests the caching and updating behavior of {@link CacheService}. It verifies that initial data retrieval for a key takes over 2000ms,
     * suggesting a direct fetch operation. Subsequent retrievals should be faster (under 2ms), indicating data is served from the cache.
     * The cache is then updated using {@code cachePut}, which should again take over 2000ms, simulating a refresh operation. Further
     * retrievals post-update should remain quick, demonstrating the cache has been successfully updated and is serving the refreshed data.
     */
    @Test
    void testCachePut() {
        assertThat(measure(() -> cacheService.getData("1"))).isGreaterThanOrEqualTo(2000L); // 2000ms
        assertThat(measure(() -> cacheService.getData("1"))).isLessThanOrEqualTo(2L); // 2ms
        assertThat(measure(() -> cacheService.getData("1"))).isLessThanOrEqualTo(2L); // 2ms

        assertThat(measure(() -> cacheService.getData("2"))).isGreaterThanOrEqualTo(2000L); // 2000ms
        assertThat(measure(() -> cacheService.getData("2"))).isLessThanOrEqualTo(2L); // 2ms
        assertThat(measure(() -> cacheService.getData("2"))).isLessThanOrEqualTo(2L); // 2ms

        assertThat(measure(() -> cacheService.cachePut("1"))).isGreaterThanOrEqualTo(2000L); // 2000ms   ' TAKE FROM ORIGINAL SOURCE AND PUT IN CACHE AGAIN  '

        assertThat(measure(() -> cacheService.getData("1"))).isLessThanOrEqualTo(2L); // 2ms
        assertThat(measure(() -> cacheService.getData("1"))).isLessThanOrEqualTo(2L); // 2ms

        assertThat(measure(() -> cacheService.getData("2"))).isLessThanOrEqualTo(2L); // 2ms                          ' NOT AFFECTED '
        assertThat(measure(() -> cacheService.getData("2"))).isLessThanOrEqualTo(2L); // 2ms                          ' NOT AFFECTED '
    }

    /**
     * Tests the cache eviction process of {@link CacheService}. Initially, it verifies that fetching data by key takes over 2000ms,
     * indicating a direct database access. Subsequent retrievals should be instant (under 2ms), reflecting cache usage.
     * <p>
     * The cache is then cleared via {@code cacheEvict}, expected to be a quick operation (under 2ms). After cache eviction, accessing
     * the same keys should again take over 2000ms, demonstrating that the data is fetched from the database, confirming the cache was
     * effectively cleared.
     */
    @Test
    void testCacheEvict() {
        assertThat(measure(() -> cacheService.getData("1"))).isGreaterThanOrEqualTo(2000L); // 2000ms
        assertThat(measure(() -> cacheService.getData("1"))).isLessThanOrEqualTo(2L); // 2ms

        assertThat(measure(() -> cacheService.getData("2"))).isGreaterThanOrEqualTo(2000L); // 2000ms
        assertThat(measure(() -> cacheService.getData("2"))).isLessThanOrEqualTo(2L); // 2ms

        assertThat(measure(() -> cacheService.getData("3"))).isGreaterThanOrEqualTo(2000L); // 2000ms
        assertThat(measure(() -> cacheService.getData("3"))).isLessThanOrEqualTo(2L); // 2ms

        assertThat(measure(() -> cacheService.cacheEvict())).isLessThanOrEqualTo(2L); // 2ms                 ' CLEAR CACHE '

        assertThat(measure(() -> cacheService.getData("1"))).isGreaterThanOrEqualTo(2000L); // 2000ms
        assertThat(measure(() -> cacheService.getData("2"))).isGreaterThanOrEqualTo(2000L); // 2000ms
        assertThat(measure(() -> cacheService.getData("3"))).isGreaterThanOrEqualTo(2000L); // 2000ms
    }


    private long measure(Supplier<String> supplier) {
        long start = System.currentTimeMillis();
        supplier.get();
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        return end - start;
    }

    private long measure(Runnable runnable) {
        long start = System.currentTimeMillis();
        runnable.run();
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        return end - start;
    }
}