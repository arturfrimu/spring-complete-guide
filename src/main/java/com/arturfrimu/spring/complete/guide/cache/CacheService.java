package com.arturfrimu.spring.complete.guide.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CacheService {

    private final CacheDao cacheDao;

    @Cacheable("data")
    public String getData(String key) {
        return cacheDao.getData(key);
    }

    @CachePut("data")
    public String cachePut(String key) {
        return cacheDao.getData(key);
    }

    @CacheEvict(value = "data", allEntries = true)
    public void cacheEvict() {}
}
