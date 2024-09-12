package com.arturfrimu.distributedcacheredis.controller;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/redis")
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class RedisController {

    StringRedisTemplate template;

    @GetMapping("/set")
    public String setKey(@RequestParam String key, @RequestParam String value) {
        template.opsForValue().set(key, value);
        return "Key set in Redis: " + key + " = " + value;
    }

    @GetMapping("/get")
    public String getKey(@RequestParam String key) {
        String value = template.opsForValue().get(key);
        if (value != null) {
            return key + " = " + value;
        } else {
            return "Key: " + key + " not found in Redis!";
        }
    }

    @GetMapping("/delete")
    public String deleteKey(@RequestParam String key) {
        Boolean result = template.delete(key);
        if (Boolean.TRUE.equals(result)) {
            return "Key deleted from Redis: " + key;
        } else {
            return "Key: " + key + " not found or could not be deleted!";
        }
    }

    @GetMapping("/increment")
    public String incrementKey(@RequestParam String key) {
        Long value = template.opsForValue().increment(key);
        return "Key incremented in Redis: " + key + " = " + value;
    }

    @GetMapping("/list/add")
    public String addToList(@RequestParam String key, @RequestParam String value) {
        template.opsForList().leftPush(key, value);
        return "Value added to Redis List: " + key + " = " + value;
    }

    @GetMapping("/list/get")
    public List<String> getList(@RequestParam String key) {
        return template.opsForList().range(key, 0, -1);
    }

    // 4. Set a key in Redis with expiration (SETEX)
    @GetMapping("/setex")
    public String setKeyWithExpiration(@RequestParam String key, @RequestParam String value, @RequestParam long seconds) {
        template.opsForValue().set(key, value, seconds, TimeUnit.SECONDS);
        return "Key set in Redis with expiration: " + key + " = " + value + " for " + seconds + " seconds";
    }

    // 5. Check if a key exists in Redis (EXISTS)
    @GetMapping("/exists")
    public String checkKeyExists(@RequestParam String key) {
        Boolean exists = template.hasKey(key);
        return exists != null && exists ? "Key exists in Redis: " + key : "Key does not exist in Redis: " + key;
    }

    // 6. Set a field in a Redis Hash (HSET)
    @GetMapping("/hash/set")
    public String setHashField(@RequestParam String key, @RequestParam String field, @RequestParam String value) {
        template.opsForHash().put(key, field, value);
        return "Hash field set: " + key + " -> " + field + " = " + value;
    }

    // 7. Get a field from a Redis Hash (HGET)
    @GetMapping("/hash/get")
    public String getHashField(@RequestParam String key, @RequestParam String field) {
        Object value = template.opsForHash().get(key, field);
        return value != null ? key + " -> " + field + " = " + value : "Field: " + field + " not found in Hash: " + key;
    }

    @GetMapping("/expire")
    public String expireKey(@RequestParam String key, @RequestParam long seconds) {
        Boolean result = template.expire(key, seconds, TimeUnit.SECONDS);
        return Boolean.TRUE.equals(result) ? "Expiration set for key: " + key : "Failed to set expiration for key: " + key;
    }

    @GetMapping("/ttl")
    public String getTTL(@RequestParam String key) {
        Long ttl = template.getExpire(key);
        return ttl >= 0 ? "TTL for key " + key + ": " + ttl + " seconds" : "Key " + key + " has no expiration or does not exist.";
    }

    @GetMapping("/append")
    public String appendToString(@RequestParam String key, @RequestParam String value) {
        Integer newLength = template.opsForValue().append(key, value);
        return "New length of key " + key + ": " + newLength;
    }

    @GetMapping("/list/pop")
    public String popFromList(@RequestParam String key) {
        String value = template.opsForList().leftPop(key);
        return value != null ? "Popped value: " + value : "List is empty or key does not exist.";
    }

    @GetMapping("/set/add")
    public String addToSet(@RequestParam String key, @RequestParam String value) {
        Long added = template.opsForSet().add(key, value);
        return added != null && added > 0 ? "Value added to set: " + value : "Failed to add value to set.";
    }

    @GetMapping("/set/get")
    public String getAllFromSet(@RequestParam String key) {
        return "Set members: " + template.opsForSet().members(key);
    }

    @GetMapping("/set/remove")
    public String removeFromSet(@RequestParam String key, @RequestParam String value) {
        Long removed = template.opsForSet().remove(key, value);
        return removed != null && removed > 0 ? "Value removed from set: " + value : "Value not found in set.";
    }

    @GetMapping("/list/length")
    public String getListLength(@RequestParam String key) {
        Long length = template.opsForList().size(key);
        return length != null ? "List length for key " + key + ": " + length : "Key not found.";
    }

    @GetMapping("/list/add/multiple")
    public String addMultipleToList(@RequestParam String key, @RequestParam List<String> value) {
        template.opsForList().leftPushAll(key, value);
        return "Values added to list: " + key;
    }

    @GetMapping("/rename")
    public String renameKey(@RequestParam String oldKey, @RequestParam String newKey) {
        template.rename(oldKey, newKey);
        return "Key renamed from " + oldKey + " to " + newKey;
    }
}
