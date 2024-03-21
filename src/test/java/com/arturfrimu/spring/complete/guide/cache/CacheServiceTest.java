package com.arturfrimu.spring.complete.guide.cache;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CacheServiceTest {

    @Autowired
    private CacheService cacheService;

    /**
     * Validates the caching behavior of {@link CacheService} by measuring the retrieval time for the same data keys.
     * The first access for a unique key is expected to take over 2000ms, simulating a time-consuming operation.
     * Subsequent accesses should be under 2ms, indicating effective caching.
     */
    @Test
    void getData() {
        assertThat(measure(() -> cacheService.getData("1"))).isGreaterThan(2000L); // 2000ms
        assertThat(measure(() -> cacheService.getData("1"))).isLessThan(2L); // 2ms
        assertThat(measure(() -> cacheService.getData("1"))).isLessThan(2L); // 2ms

        assertThat(measure(() -> cacheService.getData("2"))).isGreaterThan(2000L); // 2000ms
        assertThat(measure(() -> cacheService.getData("2"))).isLessThan(2L); // 2ms
        assertThat(measure(() -> cacheService.getData("2"))).isLessThan(2L); // 2ms

        assertThat(measure(() -> cacheService.getData("3"))).isGreaterThan(2000L); // 2000ms
        assertThat(measure(() -> cacheService.getData("3"))).isLessThan(2L); // 2ms
        assertThat(measure(() -> cacheService.getData("3"))).isLessThan(2L); // 2ms
    }

    /**
     * Tests the caching and updating behavior of {@link CacheService}. It verifies that initial data retrieval for a key takes over 2000ms,
     * suggesting a direct fetch operation. Subsequent retrievals should be faster (under 2ms), indicating data is served from the cache.
     * The cache is then updated using {@code cachePut}, which should again take over 2000ms, simulating a refresh operation. Further
     * retrievals post-update should remain quick, demonstrating the cache has been successfully updated and is serving the refreshed data.
     */
    @Test
    void getDataV2() {
        assertThat(measure(() -> cacheService.getData("1"))).isGreaterThan(2000L); // 2000ms
        assertThat(measure(() -> cacheService.getData("1"))).isLessThan(2L); // 2ms
        assertThat(measure(() -> cacheService.getData("1"))).isLessThan(2L); // 2ms

        assertThat(measure(() -> cacheService.getData("2"))).isGreaterThan(2000L); // 2000ms
        assertThat(measure(() -> cacheService.getData("2"))).isLessThan(2L); // 2ms
        assertThat(measure(() -> cacheService.getData("2"))).isLessThan(2L); // 2ms

        assertThat(measure(() -> cacheService.cachePut("1"))).isGreaterThan(2000L); // 2000ms   ' TAKE FROM ORIGINAL SOURCE AND PUT IN CACHE AGAIN  '

        assertThat(measure(() -> cacheService.getData("1"))).isLessThan(2L); // 2ms
        assertThat(measure(() -> cacheService.getData("1"))).isLessThan(2L); // 2ms

        assertThat(measure(() -> cacheService.getData("2"))).isLessThan(2L); // 2ms                          ' NOT AFFECTED '
        assertThat(measure(() -> cacheService.getData("2"))).isLessThan(2L); // 2ms                          ' NOT AFFECTED '
    }


    private long measure(Supplier<String> supplier) {
        long start = System.currentTimeMillis();
        supplier.get();
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        return end - start;
    }
}