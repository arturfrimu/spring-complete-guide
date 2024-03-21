package com.arturfrimu.spring.complete.guide.cache;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CacheDao {

    private static final Map<String, String> database = Map.of(
            "1", "a",
            "2", "b",
            "3", "c"
    );

    @SneakyThrows
    public String getData(String key) {
        Thread.sleep(2000L);
        return database.get(key);
    }
}
