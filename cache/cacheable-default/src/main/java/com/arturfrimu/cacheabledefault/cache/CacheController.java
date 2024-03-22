package com.arturfrimu.cacheabledefault.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cache/default")
public class CacheController {

    private final CacheService cacheService;

    @GetMapping("/{key}")
    public String getData(@PathVariable String key) {
        return cacheService.getData(key);
    }

    @GetMapping("/{key}/update")
    public String cachePut(@PathVariable String key) {
        return cacheService.getData(key);
    }

    @GetMapping("/evict")
    public void cacheEvict() {
        cacheService.cacheEvict();
    }
}
