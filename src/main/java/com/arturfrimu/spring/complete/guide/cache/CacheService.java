package com.arturfrimu.spring.complete.guide.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CacheService {

    private final CacheDao cacheDao;
    private final CacheManager cacheManager;

    @Cacheable("data")
    public String getData(String key) {
        return cacheDao.getData(key);
    }

    @CachePut("data")
    public String cachePut(String key) {
        return cacheDao.getData(key);
    }

    @CacheEvict(value = "data", allEntries = true)
    public void cacheEvict() {
    }

    @Cacheable("data")
    public String getDataManual(String key) {
        Cache data = cacheManager.getCache("data");
//
//        if (Objects.nonNull(data)) {
//            data.get(key);
//            data.put(key, cacheDao.getData(key));
//            data.evict(key);
//        }

        return cacheDao.getData(key);
    }
}
