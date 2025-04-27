package com.arturfrimu.hibernatecache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching    // activates Spring’s @Cacheable, too
@SpringBootApplication
public class HibernateCacheApplication {
    public static void main(String[] args) {
        SpringApplication.run(HibernateCacheApplication.class, args);
    }
}
