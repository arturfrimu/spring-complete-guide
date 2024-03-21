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

    @Test
    void getData() {
        assertThat(measure(() -> cacheService.getData("1"))).isGreaterThan(2000L); // 2000ms
        assertThat(measure(() -> cacheService.getData("1"))).isLessThan(2L); // 2ms
        assertThat(measure(() -> cacheService.getData("1"))).isLessThan(2L); // 2ms
        assertThat(measure(() -> cacheService.getData("1"))).isLessThan(2L); // 2ms

        assertThat(measure(() -> cacheService.getData("2"))).isGreaterThan(2000L); // 2000ms
        assertThat(measure(() -> cacheService.getData("2"))).isLessThan(2L); // 2ms
        assertThat(measure(() -> cacheService.getData("2"))).isLessThan(2L); // 2ms
        assertThat(measure(() -> cacheService.getData("2"))).isLessThan(2L); // 2ms

        assertThat(measure(() -> cacheService.getData("3"))).isGreaterThan(2000L); // 2000ms
        assertThat(measure(() -> cacheService.getData("3"))).isLessThan(2L); // 2ms
        assertThat(measure(() -> cacheService.getData("3"))).isLessThan(2L); // 2ms
        assertThat(measure(() -> cacheService.getData("3"))).isLessThan(2L); // 2ms
    }

    private long measure(Supplier<String> supplier) {
        long start = System.currentTimeMillis();
        supplier.get();
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        return end - start;
    }
}