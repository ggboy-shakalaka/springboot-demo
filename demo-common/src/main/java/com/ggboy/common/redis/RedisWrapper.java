package com.ggboy.common.redis;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

public class RedisWrapper {
    private RedisTemplate<String, Object> redisTemplate;

    public RedisWrapper(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public byte[] getBytes(String key) {
        return (byte[]) redisTemplate.opsForValue().get(key);
    }

    public <T> T get(String key) {
        return (T) redisTemplate.opsForValue().get(key);
    }

    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void set(String key, Object value, int exp) {
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.expire(key, exp, TimeUnit.SECONDS);
    }

    public boolean remove(String key) {
        return redisTemplate.delete(key);
    }

    public long incr(String key, int i) {
        return redisTemplate.opsForValue().increment(key, i);
    }
}
