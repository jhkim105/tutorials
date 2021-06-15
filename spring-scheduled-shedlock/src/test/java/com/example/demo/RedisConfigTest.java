package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@SpringBootTest
class RedisConfigTest {

  @Autowired
  RedisTemplate<String, String> redisTemplate;

  @Test
  void redisConnectionTest() {
    final String key = "a";
    final String data = "1";

    ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
    valueOperations.set(key, data);

    String result = valueOperations.get(key);
    assertEquals(data, result);
  }
}